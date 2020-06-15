package com.github.mariemmezghani.mytodolist;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;
import com.github.mariemmezghani.mytodolist.ViewModel.MainViewModel;

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
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        final AppDatabase mDb;
       mDb=AppDatabase.getInstance(mContext);
        final Task task = tasks.get(position);
        String description = task.getDescription();
        holder.taskDescriptionView.setText(description);
        holder.checkbox.setOnCheckedChangeListener(null);
        Boolean isChecked = task.isCompleted();
        holder.taskDescriptionView.setPaintFlags(isChecked ? holder.taskDescriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : holder.taskDescriptionView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        holder.checkbox.setChecked(isChecked);
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int currentPosition = holder.getAdapterPosition();
                Task task=tasks.get(currentPosition);
                if (isChecked) {
                    task.setCompleted(true);
                    mDb.taskDao().updateTask(task);
                } else {
                    task.setCompleted(false);
                    mDb.taskDao().updateTask(task);
                }
            }
                });
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
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            int elementId = tasks.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);

        }

    }
}
