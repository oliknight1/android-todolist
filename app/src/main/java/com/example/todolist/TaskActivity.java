package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class TaskActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    Group group;
    TaskAdapter adapter;
    DatabaseHelper db = new DatabaseHelper(this);
    ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        group = getIntent().getParcelableExtra("Group");
        populateView();

        ImageButton addTaskBtn = findViewById(R.id.addTaskBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskActivity.this, CreateTask.class);
                intent.putExtra("group", group);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        ImageButton backBtn = findViewById(R.id.backBtnTasks);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Check if a task has been successfully created
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                populateView();
            }
        }
    }

    private void populateArrayList() {
        Log.i("TAG", "populateArrayList: " + taskList.size());

        taskList.clear();

        Cursor data = db.getAllData("tasks");
        if (data.getCount() != 0) {
            Log.i("TAG", "populateArrayList: run");

            while (data.moveToNext()) {
                Gson gson = new Gson();
                String taskString = data.getString(1);
                Task task = gson.fromJson(taskString, Task.class);
                task.setId(data.getInt(0));
                // Check that the task is assigned to the current group, and that it is not already in the array
                if (task.getGroupId() == group.getId()) {
                    taskList.add(task);

                }
            }
        }

    }

    public void populateView() {
        populateArrayList();
        // Set the title of the page to the group name
        TextView title = findViewById(R.id.taskTitle);
        title.setText(group.getGroupName());

        // Sort ArrayList before sending it to the adapter

        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                // If the dates are different, sort by date
                if (!o1.getDate().equals(o2.getDate())) {
                    return o1.getDate().compareTo(o2.getDate());
                }
                // If dates are the same sort by time
                else {
                    return o1.getTime().compareTo(o2.getTime());
                }

            }
        });

        // Populating the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.taskView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TaskAdapter(taskList, group);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                // Make sure to show taskbar before deleting so can display task name
                Snackbar.make(findViewById(R.id.coordinatorLayout2), taskList.get(position).getName() + " has been deleted", Snackbar.LENGTH_LONG).show();
                db.deleteData("tasks", taskList.get(position).getId());
                populateView();

            }
        });
    }

}