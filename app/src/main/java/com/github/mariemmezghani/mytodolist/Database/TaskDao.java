package com.github.mariemmezghani.mytodolist.Database;

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
    List<Task> loadAllTasks();

    @Insert
    void insertTask(Task taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Task taskEntry);

    @Delete
    void deleteTask(Task taskEntry);

}
