package com.example.studentclient;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public class PersonalFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private TextView t_name;
    private Button btn_quit;
    private LinearLayout preson_data;
    private LinearLayout courseware;
    private LinearLayout feedback;
    private LinearLayout about_us;
    private ImageView img_head;
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1;

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
                editor.putBoolean("isLogin",false);
                editor.apply();
                startActivity(new Intent(getActivity(), LogInActivity.class));
                break;
            case R.id.preson_data:
                startActivity(new Intent(getActivity(), ModifyPersonal.class));
                break;
            case R.id.courseware:
                break;
            case R.id.feedback:
                startActivity(new Intent(getActivity(), ForgetPassword.class));
                break;
            case R.id.about_us:
                break;
        }

    }


    private void initView(View view) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();
        t_name=(TextView)view.findViewById(R.id.user_name);
        t_name.setText(preferences.getString("name","昵称"));
        //t_number=(TextView)view.findViewById(R.id.t_number);

        btn_quit=(Button)view.findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this);

        preson_data=(LinearLayout)view.findViewById(R.id.preson_data);
        preson_data.setOnClickListener(this);


//        collection=(LinearLayout)view.findViewById(R.id.collect);
//        collection.setOnClickListener(this);

        courseware=(LinearLayout)view.findViewById(R.id.courseware);
        courseware.setOnClickListener(this);

//        discussion=(LinearLayout)view.findViewById(R.id.discuss);
//        discussion.setOnClickListener(this);
//
//        phone=(LinearLayout)view.findViewById(R.id.phone);
//        phone.setOnClickListener(this);
//
//        share=(LinearLayout)view.findViewById(R.id.share);
//        share.setOnClickListener(this);

        feedback=(LinearLayout)view.findViewById(R.id.feedback);
        feedback.setOnClickListener(this);

        about_us=(LinearLayout)view.findViewById(R.id.about_us);
        about_us.setOnClickListener(this);

        img_head=(ImageView)view.findViewById(R.id.h_head);
        img_head.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        t_name.setText(preferences.getString("name","昵称"));
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            !=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_SDCARD_PERMISSION_REQUEST_CODE);

                            }
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeFile("/sdcard/avatar.jpg", options);
        img_head.setImageBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getContext(), "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
