package com.example.studentclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class HomeFragment extends Fragment {


    private LinearLayout myClassroom;
    private LinearLayout joinClassroom;
    private Button moreCourse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myClassroom = (LinearLayout)getActivity().findViewById(R.id.my_classroom);
        myClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to to my class activity
                startActivity(new Intent(getActivity(),LoginInActivity.class));
            }
        });

        joinClassroom = (LinearLayout)getActivity().findViewById(R.id.join_classroom);
        joinClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to join class activity
//                startActivity(new Intent(getActivity(),LoginInActivity.class));
            }
        });

        moreCourse = (Button)getActivity().findViewById(R.id.more_course);
        moreCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               go to course_table activity
//
            }
        });
    }
}
