package com.github.mariemmezghani.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mariemmezghani.mytodolist.Model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> tasks;
    public TaskAdapter(Context context){
        mContext=context;
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view

        View view = LayoutInflater.from(mContext)

                .inflate(R.layout.task_layout, parent, false);



        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task=tasks.get(position);
        String description=task.getDescription();
        holder.taskDescriptionView.setText(description);


    }

    @Override
    public int getItemCount() {
        if (tasks==null) {
            return 0;
        }
        return tasks.size();
    }
    public void setList(List<Task> tasks){
        this.tasks=tasks;

    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskDescriptionView;
        public TaskViewHolder(View itemView) {
            super(itemView);
            taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);

        }

    }
}
