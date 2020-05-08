package com.github.mariemmezghani.mytodolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private List<Task> tasks;

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    public TaskAdapter(Context context, ItemClickListener listener){
        mContext=context;
        mItemClickListener=listener;
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
        final Task task=tasks.get(position);
        String description=task.getDescription();
        Boolean isChecked=task.isCompleted();
        holder.taskDescriptionView.setText(description);
        holder.checkbox.setChecked(isChecked);
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
        notifyDataSetChanged();

    }
    public List<Task> getTasks() {

        return tasks;

    }
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView taskDescriptionView;
        CheckBox checkbox;
        public TaskViewHolder(final View itemView) {
            super(itemView);
            taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);
            checkbox=(CheckBox) itemView.findViewById(R.id.cbItemCheck);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                    int position=getAdapterPosition();
                    Task task=tasks.get(position);
                    task.setCompleted(checkbox.isChecked());
                    AppDatabase.getInstance(itemView.getContext().getApplicationContext()).taskDao().updateTask(task);
                }
            });}});
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            int elementId = tasks.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);

        }

    }
}
