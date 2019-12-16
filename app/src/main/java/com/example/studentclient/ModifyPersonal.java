package com.example.studentclient;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyPersonal extends AppCompatActivity implements View.OnClickListener{

    private Button btn_submit;
    private TextView t_id;
    private EditText name;
    private EditText collage;
    private EditText major;
    private EditText tel;
    private EditText mail;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal);
        initview();
    }


    private void initview() {

        t_id=findViewById(R.id.id);
        name=(EditText)findViewById(R.id.name);
        collage=(EditText)findViewById(R.id.collage);
        major=(EditText)findViewById(R.id.major);
        tel=(EditText)findViewById(R.id.tel);
        mail=(EditText)findViewById(R.id.mail);

        btn_submit=(Button)findViewById(R.id.submit);
        btn_submit.setOnClickListener(this);

        img_back=findViewById(R.id.back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.submit:
                break;
        }
    }
}
