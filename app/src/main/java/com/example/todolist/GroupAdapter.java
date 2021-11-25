package com.example.todolist;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class GroupAdapter extends BaseAdapter {

    private final Context myContext;
    private ArrayList<Group> groups;
    private LayoutInflater layoutInflater;


    public GroupAdapter(Context myContext, ArrayList<Group> groups) {
        this.myContext = myContext;
        this.groups = groups;
        layoutInflater = (LayoutInflater.from(myContext));
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    // Not used, but necessary to override
    @Override
    public Object getItem(int position) {
        return null;
    }

    // Not used, but necessary to override
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;

        if (convertView == null) {
            v = layoutInflater.inflate(R.layout.group_box, parent, false);
        } else {
            v = convertView;
        }
        ImageButton groupIcon = v.findViewById(R.id.imageBtn);
        final Group currentGroup = groups.get(position);
        // Sets the background image to the imgID defined on object creation
        groupIcon.setImageResource(currentGroup.getImdId());
        // Sets the background colour to the colour defined in object creation
        LinearLayout btnBg = v.findViewById(R.id.btnBg);
        GradientDrawable drawable = (GradientDrawable) btnBg.getBackground();
        drawable.setColor(currentGroup.getBgCol());
        TextView groupText = v.findViewById(R.id.groupText);
        groupText.setText(currentGroup.getGroupName());
        // Apply onClick listener to open Tasks page to all image buttons and the backgrounds so the user can click either one
        // This is why it needs to be done inside adapter
        // Pass the Group object through to the next activity
        btnBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, TaskActivity.class);
                intent.putExtra("Group", currentGroup);

                myContext.startActivity(intent);
            }
        });
        groupIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, TaskActivity.class);
                intent.putExtra("Group", currentGroup);
                myContext.startActivity(intent);
            }
        });

        return v;
    }

}