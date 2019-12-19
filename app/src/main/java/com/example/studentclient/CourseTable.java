package com.example.studentclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class CourseTable extends AppCompatActivity {

    private GridLayout gridLayout;
    private ImageView back;
    private ImageView addNewCourse;
    private Spinner courseDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_table);

        back = (ImageView)findViewById(R.id.back_home_main);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        setCourse("1","1","yes");
        addNewCourse = (ImageView)findViewById(R.id.add_new_course);
        addNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseTable.this);
                final View view = LayoutInflater.from(CourseTable.this).inflate(R.layout.dialog_add_course,null);
                builder.setTitle("添加课程");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setCourse("1","1","yes");//添加课程
                    }
                });
                builder.show();
            }
        });

    }


    private void setCourse(String row, String col, String course){
        Resources resources = getResources();
        String name = "item_" + row + "_" + col;
        int resId = resources.getIdentifier(name,"id",this.getPackageName());
        TextView textView = (TextView)findViewById(resId);
        textView.setText(course);

    }

}
