package com.example.todolist;

import android.text.Editable;
import android.text.TextUtils;

import java.util.ArrayList;

// Validator class is used to validate the inputs from creating a Task / Group
// Used to make the code much more readable
public class Validator {

    // Method Overloading used to make it even clearer

    // Returns true if the text is NOT empty
    // Returns false is the text IS empty
    public static boolean validateText(Editable data) {
        return !TextUtils.isEmpty(data);
    }

    // Returns true if the text is NOT empty
    // Returns false is the text IS empty
    public static boolean validateText(String data) {
        return !TextUtils.isEmpty(data);
    }

    // Returns true if the int is NOT 0
    // Returns false is the int IS 0
    public static boolean validateNum(int data) {
        return data != 0;
    }

    public static boolean validateLength(String data, String comparison, int length) {
        data = data.trim();
        comparison = comparison.trim();
        if (comparison.equals(">")) {
            if (data.length() > length) {
                return false;
            }
        }
        if (comparison.equals("<")) {
            if (data.length() < length) {
                return false;
            }
        }
        return true;
    }

    // Check if the group exists in arraylist
    public static boolean itemExists(Group data, ArrayList<Group> dataList) {
        for (Group group : dataList) {
            if (group.getId() == data.getId()) {
                return true;
            }
        }
        return false;
    }

    // Check if the group exists in arraylist
    public static boolean itemExists(Task data, ArrayList<Task> dataList) {
        for (Task task : dataList) {
            if (data.getId() == task.getId()) {
                return true;
            }
        }
        return false;
    }
}