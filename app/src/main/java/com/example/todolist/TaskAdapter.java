package com.example.todolist;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> mTaskList;
    private Group mGroup;
    private OnItemClickListener mListener;


    // Create a custom onItemClickListener as RecyclerView doesn't have one
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTaskName;
        public TextView mTaskDate;
        public TextView mTaskTime;
        public View mDivider;
        public LinearLayout mTaskContainer;
        public ImageView mDeleteTaskBtn;

        public ViewHolder(View taskView, final OnItemClickListener listener) {
            super(taskView);
            mTaskName = taskView.findViewById(R.id.taskName);
            mTaskDate = taskView.findViewById(R.id.taskDate);
            mTaskTime = taskView.findViewById(R.id.taskTime);
            mDivider = taskView.findViewById(R.id.divider);
            mTaskContainer = taskView.findViewById(R.id.taskContainer);
            mDeleteTaskBtn = taskView.findViewById(R.id.deleteTaskBtn);

            // Set an onClick listener to the recyclerView via taskView
            // When recyclerView is clicked, get the position that is clicked, and send it to onItemClick
            taskView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }

                    }
                }
            });
            mDeleteTaskBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }

                    }
                }
            });
        }
    }

    public TaskAdapter(ArrayList<Task> taskList, Group group) {
        mTaskList = taskList;
        mGroup = group;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Task prevTask = null;
        final Task currentTask = mTaskList.get(position);
        if (position > 0) {
            int prevPosition = position - 1;
            prevTask = mTaskList.get(prevPosition);
        }
        if (prevTask != null && currentTask.getDate().equals(prevTask.getDate())) {
            holder.mTaskDate.setVisibility(View.GONE);
            holder.mDivider.setVisibility(View.GONE);

        }

        holder.mTaskDate.setText(currentTask.getDate());
        holder.mTaskName.setText(currentTask.getName());
        holder.mTaskTime.setText("Start Time: " + currentTask.getTime());
        GradientDrawable drawable = (GradientDrawable) holder.mTaskContainer.getBackground();
        drawable.setColor(mGroup.getBgCol());

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

}
