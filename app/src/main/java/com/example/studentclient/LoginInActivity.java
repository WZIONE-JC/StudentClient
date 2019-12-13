package com.example.studentclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginInActivity extends AppCompatActivity {

    private Button loginIn;
    private Button forgetPsw;
    private Button signUp;
    private EditText account;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        loginIn = (Button)findViewById(R.id.button_login_in);
        forgetPsw = (Button)findViewById(R.id.button_forget_psw);
        signUp = (Button)findViewById(R.id.button_sign_up);
        account = (EditText)findViewById(R.id.text_account);
        password = (EditText)findViewById(R.id.text_password);

        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!account.getText().toString().trim().equals("")){
                    if (!password.getText().toString().trim().equals("")){

                        //登录判断密码是否相同

                        Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginInActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginInActivity.this,"请输入用户名！",Toast.LENGTH_SHORT).show();

                }


            }
        });

        forgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
