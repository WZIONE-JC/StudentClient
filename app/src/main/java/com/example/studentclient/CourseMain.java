package com.example.studentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CourseMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_main);

        ImageView back = (ImageView)findViewById(R.id.back_my_class);
        LinearLayout courseWare = (LinearLayout) findViewById(R.id.course_ware_class);
        LinearLayout homework = (LinearLayout)findViewById(R.id.homework);
        LinearLayout signIn = (LinearLayout)findViewById(R.id.sign_in);
        LinearLayout test = (LinearLayout)findViewById(R.id.test);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CourseMain.this,MyClass.class));
                finish();
            }
        });

        courseWare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

    }
}
