package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;


public class Group implements Parcelable {
    private String groupName;
    private Integer imgId;
    private Integer col;
    private int id;

    public Group(String groupName, int imgId, int col) {
        this.groupName = groupName;
        this.imgId = imgId;
        this.col = col;
    }

    // Parcelable implementation
    protected Group(Parcel in) {
        groupName = in.readString();
        imgId = in.readInt();
        col = in.readInt();
        id = in.readInt();

    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeInt(imgId);
        dest.writeInt(col);
        dest.writeInt(id);

    }

    // Getter and setter methods

    public String getGroupName() {
        return groupName;
    }

    public int getImdId() {
        return imgId;
    }

    public int getBgCol() {
        return col;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

