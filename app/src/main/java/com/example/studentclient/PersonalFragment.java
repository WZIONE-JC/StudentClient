package com.example.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PersonalFragment extends Fragment implements View.OnClickListener{

    private TextView t_name;
    private Button btn_quit;
    private LinearLayout preson_data;
    private LinearLayout collection;
    private LinearLayout courseware;
    private LinearLayout discussion;
    private LinearLayout phone;
    private LinearLayout share;
    private LinearLayout feedback;
    private LinearLayout about_us;
    private ImageView img_head;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal,null);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.h_head: //选择头像
                startActivity(new Intent(getActivity(), SelectPicture.class));
                break;
            case R.id.btn_quit://退出登陆
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginInActivity.class));
                break;
            case R.id.preson_data:
                startActivity(new Intent(getActivity(), ModifyPersonal.class));
                break;
            case R.id.collect:
                break;
            case R.id.courseware:
                break;
            case R.id.discuss:
                break;
            case R.id.phone:
                break;
            case R.id.share:
                break;
            case R.id.feedback:
                break;
            case R.id.about_us:
                break;
        }

    }


    private void initView(View view) {

        t_name=(TextView)view.findViewById(R.id.user_name);
        //t_number=(TextView)view.findViewById(R.id.t_number);

        btn_quit=(Button)view.findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);

        preson_data=(LinearLayout)view.findViewById(R.id.preson_data);
        preson_data.setOnClickListener(this);

        collection=(LinearLayout)view.findViewById(R.id.collect);
        collection.setOnClickListener(this);

        courseware=(LinearLayout)view.findViewById(R.id.courseware);
        courseware.setOnClickListener(this);

        discussion=(LinearLayout)view.findViewById(R.id.discuss);
        discussion.setOnClickListener(this);

        phone=(LinearLayout)view.findViewById(R.id.phone);
        phone.setOnClickListener(this);

        share=(LinearLayout)view.findViewById(R.id.share);
        share.setOnClickListener(this);

        feedback=(LinearLayout)view.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);

        about_us=(LinearLayout)view.findViewById(R.id.about_us);
        about_us.setOnClickListener(this);

        img_head=(ImageView)view.findViewById(R.id.h_head);
        img_head.setOnClickListener(this);

    }



}
