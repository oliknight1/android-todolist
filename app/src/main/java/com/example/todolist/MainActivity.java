package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    ArrayList<Group> groups = new ArrayList<>();

    GridView gridView;
    GroupAdapter adapter;


    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);
        adapter = new GroupAdapter(this, groups);
        populateView();

        ImageButton addGroupBtn = findViewById(R.id.addGroupBtn);
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult( new Intent(MainActivity.this, AddGroupPage.class), REQUEST_CODE);
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
        Cursor data = db.getAllData("groups");

        if (data.getCount() != 0) {
            while (data.moveToNext()) {
                Gson gson = new Gson();
                String groupString = data.getString(1);
                Group group = gson.fromJson(groupString, Group.class);
                group.setId(data.getInt(0));

                // Only add the group to the arraylist if it alreadye exists
                if (!Validator.itemExists(group,groups)) {
                    groups.add(group);
                }

            }
        }
    }



    private void populateView() {
        populateArrayList();
        gridView.setAdapter(adapter);
    }
}