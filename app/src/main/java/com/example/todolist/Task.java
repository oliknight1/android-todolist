package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;


public class Task implements Parcelable {
    private String name;
    private String date;
    private String time;
    private Integer groupId;


    private Integer id;


    public Task(String name, String date, String time, Integer groupId) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.groupId = groupId;

    }

    // Parcelable implementation
    protected Task(Parcel in) {
        name = in.readString();
        date = in.readString();
        time = in.readString();
        groupId = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(groupId);
        dest.writeInt(id);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }


    public int getGroupId() {
        return groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

