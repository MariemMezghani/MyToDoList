package com.github.mariemmezghani.mytodolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;
import com.github.mariemmezghani.mytodolist.ViewModel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {
    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
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
        mAdapter=new TaskAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

         /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        // Called when a user swipes left or right on a ViewHolder
        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    //  get the position from the viewHolder parameter
                    int position = viewHolder.getAdapterPosition();
                    List<Task> tasks = mAdapter.getTasks();
                    // Call deleteTask in the taskDao with the task at that position
                    mDb.taskDao().deleteTask(tasks.get(position));
                    // Call setUpViewModel method to refresh the UI
                }
            });
        }
    }).attachToRecyclerView(mRecyclerView);

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
        setUpViewModel();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setUpViewModel() {
        MainViewModel viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasksList) {
                Log.d(TAG, "recieve database update from ViewModel");
                mAdapter.setList(tasksList);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);

    }

}
