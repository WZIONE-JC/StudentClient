package com.example.studentclient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class SelectPicture extends AppCompatActivity  implements View.OnClickListener{

    private TextView text_take_photo;
    private TextView text_pick_photo;
    private Button btn_cancel;
    private ImageView img_head;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);
        initview();
    }

    private void initview() {
        btn_cancel=findViewById(R.id.cancel);
        text_take_photo=(TextView)findViewById(R.id.take_photo);
        text_pick_photo=(TextView)findViewById(R.id.pick_photo);
        //img_head = (ImageView) LayoutInflater.from(SelectPicture.this).inflate(R.layout.fragment_personal, null).findViewById(R.id.h_head);
        btn_cancel.setOnClickListener(this);
        text_take_photo.setOnClickListener(this);
        text_pick_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                this.finish();
                break;
            case R.id.take_photo:
                takePhoto();
                break;
            case R.id.pick_photo:
                pickPhoto();
                break;
        }
    }

    //打开相册
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    //拍照
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, TAKE_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == TAKE_PHOTO || requestCode == CHOOSE_PHOTO) {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
//                img_head.setImageBitmap(bitmap);// 显示图片
            }
        }
    }

}