package com.github.mariemmezghani.mytodolist.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Model.Task;

public class AddTaskViewModel extends ViewModel {

    // Add a task member variable for the Task object wrapped in a LiveData
    private LiveData<Task> task;

    //Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    //Create a getter for the task variable
    public LiveData<Task> getTask() {
        return task;
    }
}
