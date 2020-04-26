package com.github.mariemmezghani.mytodolist;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mariemmezghani.mytodolist.Database.AppDatabase;
import com.github.mariemmezghani.mytodolist.Database.AppExecutors;
import com.github.mariemmezghani.mytodolist.Model.Task;
import com.github.mariemmezghani.mytodolist.R;

public class AddTaskActivity extends AppCompatActivity {

    // Member variable for the Database
    private AppDatabase mDb;
    //Extra for the task ID to be retrieved after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    //constant for default tadk id
    private static final int DEFAULT_TASK_ID= -1;
    private int mTaskId = DEFAULT_TASK_ID;
    EditText mEditText;
    Button mButton;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_activity);
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
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(INSTANCE_TASK_ID,mTaskId);
        super.onSaveInstanceState(outState);
    }
     //onClickAddTask is called when the "ADD" button is clicked.
    public void onClickAddTask() {
        String description = mEditText.getText().toString();
        final Task taskEntry = new Task(description);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().insertTask(taskEntry);
                finish();
            }
        });

    }
}
