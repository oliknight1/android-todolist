package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GroupIconAdapter extends BaseAdapter {
    private ArrayList<Integer> mIconList;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public GroupIconAdapter(ArrayList<Integer> iconList, Context context){
        mIconList = iconList;
        mContext = context;
        layoutInflater = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return mIconList.size();
    }

    @Override
    public Object getItem(int position) {
        return mIconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;

        if (convertView == null) {
            v = layoutInflater.inflate(R.layout.group_icon_grid_layout, parent, false);
        } else {
            v = convertView;
        }

        final ImageView icon = v.findViewById(R.id.groupIcon);
        icon.setImageResource(mIconList.get(position));

        return v ;
    }
}
