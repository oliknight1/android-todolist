package com.example.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView taskName;
    TextView datePicker;
    TextView timePicker;
    ImageView saveTaskBtn;
    Group group;
    Task newTask;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // The Group object that started the activity
        // Used to give the ID of the group to the Task object we will create
        group = getIntent().getParcelableExtra("group");
        taskName = findViewById(R.id.taskName);
        saveTaskBtn = findViewById(R.id.saveTask);
        datePicker = findViewById(R.id.dateText);
        timePicker = findViewById(R.id.timeText);

        // Opens the date picker dialog on click
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        // Opens the time picker dialog on click;
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        // Return to the Task page
        ImageButton backBtn = findViewById(R.id.backBtnAddGroup);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();

            }
        });
        saveTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ArrayList of errors passed to the AlertHelper class
                ArrayList<String> errors = new ArrayList<>();

                // If input is empty add an error
                if (!Validator.validateText(taskName.getText().toString().trim())) {
                    errors.add("Task Name");
                }
                if (!Validator.validateText(datePicker.getText().toString().trim())) {
                    errors.add("Task Date");
                }
                if (!Validator.validateText(timePicker.getText().toString().trim())) {
                    errors.add("Task Time");
                }
                // If the errors ArrayList is not empty, show alert
                if (!errors.isEmpty()) {
                    AlertHelper.displayAlert(errors, CreateTask.this);

                } else {

                    // If all inputs are valid, create the Task object and store it in the database
                    newTask = new Task(taskName.getText().toString(), datePicker.getText().toString(), timePicker.getText().toString(), group.getId());

                    Gson gson = new Gson();
                    String json = gson.toJson(newTask);
                    db.addData(json,"tasks","task_object");
                    setResult(Activity.RESULT_OK, new Intent());
                    finish();


                }
            }
        });


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        // Format to short hand date
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        String currentDate = df.format(cal.getTime());
        datePicker.setText(currentDate);

    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int min) {
        // Format the string so that time will display as 03:03 rather than 3:3
        timePicker.setText(String.format("%02d:%02d", hour, min));
    }

}
