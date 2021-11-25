package com.example.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

class AlertHelper {

    static void displayAlert(ArrayList<String> errors, Context context) {
        // Convert the arraylist to an array so it can be passed to .setItems()
       String[] convertedErrors = errors.toArray(new String[0]);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Please fill out the following fields:")
                .setItems(convertedErrors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // We just want the default action so leave this empty
                    }
                });

        // When the "OKAY" button is pressed it will return you to the homepage
        final AlertDialog positiveBtn = alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // We just want the default action so leave this empty
            }
        }).create();

        // Customize the button to make it more legible
        positiveBtn.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn = positiveBtn.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setBackgroundColor(Color.LTGRAY);
                btn.setTextColor(Color.BLACK);
                btn.setBackgroundResource(R.drawable.alert_btn);
                btn.setWidth(200);
            }
        });
        positiveBtn.show();
    }
    static void displayAlertNoList(String title,String message,Context context) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title)
                .setMessage(message);

        // When the "OKAY" button is pressed it will return you to the homepage
        final AlertDialog positiveBtn = alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // We just want the default action so leave this empty
            }
        }).create();

        // Customize the button to make it more legible
        positiveBtn.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btn = positiveBtn.getButton(AlertDialog.BUTTON_POSITIVE);
                btn.setBackgroundColor(Color.LTGRAY);
                btn.setTextColor(Color.BLACK);
                btn.setBackgroundResource(R.drawable.alert_btn);
                btn.setWidth(200);
            }
        });
        positiveBtn.show();
    }
}





