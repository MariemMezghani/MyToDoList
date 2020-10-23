package com.github.mariemmezghani.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;
import com.github.mariemmezghani.mytodolist.ViewModel.AddTaskViewModel;
import com.github.mariemmezghani.mytodolist.ViewModel.AddTaskViewModelFactory;

public class AddTaskActivity extends AppCompatActivity {

    // Member variable for the Database
    private AppDatabase mDb;
    //Extra for the task ID to be retrieved after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    //constant for default tadk id
    private static final int DEFAULT_TASK_ID= -1;
    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    private int mTaskId = DEFAULT_TASK_ID;
    EditText mEditText;
    Button mButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        // Status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        setTitle("My Task");

        mDb = AppDatabase.getInstance(getApplicationContext());
        mEditText = findViewById(R.id.editTextTaskDescription);
        mButton = findViewById(R.id.addButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddTask();
            }

        });
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {

            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);

        }
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText(R.string.update_button);
            if (mTaskId == DEFAULT_TASK_ID) {
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);
                viewModel.getTask().observe(this, new Observer<Task>() {
                    @Override
                    public void onChanged(Task taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        if (taskEntry == null) {
                            return;
                        }
                        mEditText.setText(taskEntry.getDescription());
                    }
                });

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(INSTANCE_TASK_ID,mTaskId);
        super.onSaveInstanceState(outState);
    }

     //onClickAddTask is called when the "ADD" button is clicked.
    public void onClickAddTask() {
        String description = mEditText.getText().toString();
        Boolean checked;
        if ((description==null)||(description.length() <= 0)) { return;}
            if (mTaskId==DEFAULT_TASK_ID) {
            checked=false;
        }else{
                    Task task = mDb.taskDao().loadTask(mTaskId);
                    checked=task.isCompleted();

                }

            final Task task = new Task(description,checked);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId==DEFAULT_TASK_ID) {
                    mDb.taskDao().insertTask(task);
                }else{
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();

            }
        });

    }
}
