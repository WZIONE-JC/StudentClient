package com.example.studentclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectPicture extends AppCompatActivity  implements View.OnClickListener{

    private TextView text_take_photo;
    private TextView text_pick_photo;
    private Button btn_cancel;

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
                break;
            case R.id.pick_photo:
                break;
        }
    }
}
