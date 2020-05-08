package com.github.mariemmezghani.mytodolist.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.github.mariemmezghani.mytodolist.Model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY id")
    LiveData<List<Task>> loadAllTasks();

    @Insert
    void insertTask(Task taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task taskEntry);

    @Delete
    void deleteTask(Task taskEntry);

    // Create a Query method named loadTaskById that receives an int id and returns a Task Object
    // The query for this method should get all the data for that id in the Task table
    @Query("SELECT * FROM task WHERE id = :id")
    LiveData<Task> loadTaskById(int id);
}
