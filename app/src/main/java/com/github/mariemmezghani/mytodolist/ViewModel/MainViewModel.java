package com.github.mariemmezghani.mytodolist.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Model.Task;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Task>> tasks;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database=AppDatabase.getInstance(this.getApplication());
        tasks=database.taskDao().loadAllTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }
}
