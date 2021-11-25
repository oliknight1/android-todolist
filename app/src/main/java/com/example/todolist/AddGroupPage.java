package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;


public class AddGroupPage extends AppCompatActivity {
    // Stores the colour values for the colour picker
    private ArrayList<String> colours = new ArrayList<>();

    // Button used to open the colour picker
    Button colPickerBtn;
    // Used to check if the group colour has been picked, if 0 it means it hasn't been picked
    int groupCol = 0;
    // Used to check if the group icon has been picked, if 0 it means it hasn't been pciekd
    int groupIcon = 0;
    // Used for the previous gridview selected item, -1 means not set
    int prevPosition = -1;

    // Database connection
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_page);


        // Loop through the drawable folder and add the icon files to an arraylist
        Field[] fields = R.drawable.class.getFields();
        ArrayList<Integer> groupIcons = new ArrayList<>();
        for (Field field : fields) {
            // Search for files beginning with icon
            if (field.getName().startsWith("icon")) {
                try {
                    groupIcons.add(field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        // Set the adapter for the GridView displaying the icons
        final GridView iconGrid = findViewById(R.id.iconGrid);
        iconGrid.setAdapter(new GroupIconAdapter(groupIcons, this));

        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int currentItem = (int) parent.getItemAtPosition(position);
                // If the user selected the item that is already selected, set the groupIcon to 0 in order to deselect it
                if (currentItem != groupIcon) {
                    groupIcon = currentItem;
                } else {
                    groupIcon = 0;
                }
                LinearLayout item = (LinearLayout) view;

                // When an item as been selected, change background colour
                item.setBackgroundColor(Color.parseColor("#494949"));

                // Check if a item has already been selected, and remove the background from it if it has
                // if prevPosition = -1 then that would mean an item has not been previously selected
                if (prevPosition != -1) {
                    LinearLayout prevItem = (LinearLayout) iconGrid.getChildAt(prevPosition);
                    prevItem.setBackgroundColor(0x00000000);
                }
                prevPosition = position;
            }
        });
        // Populate the arraylist for the colour picker with colours
        colours.add("#3D7AFD");
        colours.add("#6e138a");
        colours.add("#054a00");
        colours.add("#33B864");
        colours.add("#FE2C54");
        colours.add("#D81B60");
        colours.add("#8a0017");
        colours.add("#FFAB0F");

        // Return to previous page when button is clicked
        ImageButton backBtn = findViewById(R.id.backBtnAddGroup);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGroupPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Open the colour picker when the button is pressed
        colPickerBtn = findViewById(R.id.colPickerBtn);
        colPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColourPicker();
            }
        });

        // Saving the new group
        ImageButton saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                // ArrayList containing any errors from the inputs
                ArrayList<String> errors = new ArrayList<>();
                EditText nameInput = findViewById(R.id.groupName);
                // If input is empty, add an error to the ArrayList
                if (!Validator.validateText(nameInput.getText().toString().trim())) {
                    errors.add("Group Name");
                }
                // if input is over a certain amount of characters display an error
                if (!Validator.validateLength(nameInput.getText().toString().trim(), ">", 9)) {
                    errors.add("Group Name must be less than 10 characters");
                }
                // If number is 0 add an error
                if (!Validator.validateNum(groupIcon)) {
                    errors.add("Group Icon");
                }
                if (!Validator.validateNum(groupCol)) {
                    errors.add("Group Colour");
                }
                // If the error arraylist is not empty, then display an alert
                if (!errors.isEmpty()) {
                    AlertHelper.displayAlert(errors, AddGroupPage.this);
                } else {
                    Group newGroup = new Group(nameInput.getText().toString(), groupIcon, groupCol);

                    Gson gson = new Gson();
                    String json = gson.toJson(newGroup);
                    db.addData(json,"groups","group_object");
                    setResult(Activity.RESULT_OK, new Intent());
                    finish();
                }
            }
        });
    }


    // Opens up the colour picker
    private void openColourPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.setColors(colours)
                .setColumns(4)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        // Set the colour of the button to the selected button
                        colPickerBtn.setBackgroundColor(color);
                        // Set to check if a colour has been picked
                        groupCol = color;
                    }

                    @Override
                    public void onCancel() {
                        // Make sure that the button background doesn't change if cancelled
                        if (groupCol != 0) {
                            colPickerBtn.setBackgroundColor(groupCol);
                        }
                    }
                })
                .show();

    }


}
