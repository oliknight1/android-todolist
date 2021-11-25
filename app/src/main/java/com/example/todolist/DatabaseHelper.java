package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "to_do_list.db";
    private static final String TABLE1 = "groups";
    private static final String TABLE2 = "tasks";

    private static final String GROUP_ID = "group_id";
    private static final String GROUP_OBJECT = "group_object";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLE1 + " (" + GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP_OBJECT + " TEXT)";
        String createTable2 = "CREATE TABLE tasks (task_id INTEGER PRIMARY KEY AUTOINCREMENT, task_object TEXT, group_id TEXT)";

        db.execSQL(createTable1);
        db.execSQL(createTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);

        onCreate(db);
    }

    // Used to add either a Group or Task to the database
    public boolean addData(String data, String table, String col) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col, data);
        // if result = -1, then the data was inserted incorrectly
        long result = db.insert(table, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    // Grabs all data from a defined table
    public Cursor getAllData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + table;
        return db.rawQuery(query, null);
    }

    // Used to delete tasks from the database
    public boolean deleteData(String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(table, "task_id= ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
