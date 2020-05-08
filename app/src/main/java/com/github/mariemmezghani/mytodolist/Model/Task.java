package com.github.mariemmezghani.mytodolist.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="task")
public class Task {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String description;
    private boolean completed;
    public Task(int id, String description, Boolean completed){
        this.id=id;
        this.description=description;
        this.completed=completed;
    }
    @Ignore
    public Task(String description){
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
