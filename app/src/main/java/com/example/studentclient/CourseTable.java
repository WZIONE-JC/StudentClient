package com.example.studentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;


public class CourseTable extends AppCompatActivity {

    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);
    }

}
