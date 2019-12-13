package com.example.studentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private ImageView back;
    private Button signUp;
    private EditText account;
    private EditText password;
    private EditText passwordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = (ImageView)findViewById(R.id.ic_back);
        account = (EditText)findViewById(R.id.text_sign_up_account);
        password = (EditText)findViewById(R.id.text_sign_up_password);
        passwordAgain = (EditText)findViewById(R.id.text_sign_up_password_again);
        signUp = (Button)findViewById(R.id.button_sign_up_ok);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginInActivity.class);
                startActivity(intent);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!account.getText().toString().trim().equals("")){
                    if (!password.getText().toString().trim().equals("")){
                        if (!passwordAgain.getText().toString().trim().equals("")){
                            if (password.getText().toString().trim().equals(passwordAgain.getText().toString().trim())){
                                //注册账户！！！

                                Toast.makeText(SignUpActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this,LoginInActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignUpActivity.this,"两次密码不同，请重新输入！",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignUpActivity.this,"请再次输入密码！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignUpActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignUpActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
