package com.example.studentclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginInActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Button loginIn;
    private Button forgetPsw;
    private Button signUp;
    private EditText account;
    private EditText password;
    private String username;
    private String pass;
    private String path = "http://122.51.186.91:8081/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        loginIn = (Button)findViewById(R.id.button_login_in);
        forgetPsw = (Button)findViewById(R.id.button_forget_psw);
        signUp = (Button)findViewById(R.id.button_sign_up);
        account = (EditText)findViewById(R.id.text_account);
        password = (EditText)findViewById(R.id.text_password);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!account.getText().toString().trim().equals("")){
                    if (!password.getText().toString().trim().equals("")){
                        username = account.getText().toString().trim();
                        pass = password.getText().toString().trim();
                        sendAccountAndPassword();

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


    private void sendAccountAndPassword(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_id",username)
                            .add("password",pass)
                            .build();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(new URL(path))
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    JSONObject result = new JSONObject(responseData);

//                    Log.d("da",result.toString());
                    if (result.has("state")){
                        int state = result.getInt("state");
//                        Log.d("state",String.valueOf(state));
                        if (state == 0){
                            editor.putBoolean("isLogin",true);
                            editor.apply();
                            Intent intent = new Intent(LoginInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Looper.prepare();
                            Toast.makeText(LoginInActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }else {
                        Looper.prepare();
                        Toast.makeText(LoginInActivity.this,"用户不存在！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
