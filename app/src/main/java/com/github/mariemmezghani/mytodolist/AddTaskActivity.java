package com.github.mariemmezghani.mytodolist;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mariemmezghani.mytodolist.Model.Task;
import com.github.mariemmezghani.mytodolist.R;

public class AddTaskActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_activity);
    }
     //onClickAddTask is called when the "ADD" button is clicked.

    public void onClickAddTask(View view) {

    }
}
