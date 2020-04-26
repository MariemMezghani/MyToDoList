package com.github.mariemmezghani.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    TaskAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the RecyclerView to its corresponding view
        mRecyclerView = (RecyclerView) findViewById(R.id.toDoRecyclerView);

        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter=new TaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.add_fab);
        fabButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                // Create a new intent to start an AddTaskActivity

                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);

                startActivity(addTaskIntent);

            }

        });
        mDb=AppDatabase.getInstance(getApplicationContext());
    }
    @Override
    protected void onResume(){
        super.onResume();
        //call the diskIo execute methodeand implement its run method
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks=mDb.taskDao().loadAllTasks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setList(tasks);
                    }
                });
            }
        });
    }
}
