package com.example.studentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SignUpActivity extends AppCompatActivity {
    private ImageView back;
    private Button signUp;
    private EditText account;
    private EditText password;
    private EditText passwordAgain;
    private EditText name;
    private String userId;
    private String userName;
    private String pass;
    private String path = "http://122.51.186.91:8081//student/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back = (ImageView)findViewById(R.id.ic_back);
        account = (EditText)findViewById(R.id.text_sign_up_account);
        password = (EditText)findViewById(R.id.text_sign_up_password);
        passwordAgain = (EditText)findViewById(R.id.text_sign_up_password_again);
        name = (EditText)findViewById(R.id.text_sign_up_name);
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
                if (!name.getText().toString().trim().equals("")){
                    if (!account.getText().toString().trim().equals("")){
                        if (!password.getText().toString().trim().equals("")){
                            if (!passwordAgain.getText().toString().trim().equals("")){
                                if (password.getText().toString().trim().equals(passwordAgain.getText().toString().trim())){
                                    userName =name.getText().toString().trim();
                                    userId = account.getText().toString().trim();
                                    pass = password.getText().toString().trim();
                                    //注册账户！！！
                                    sendAccountAndPassword();
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
                        Toast.makeText(SignUpActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignUpActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
                }
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
                            .add("user_id",userId)
                            .add("password",MD5(pass))
                            .add("user_name",userName)
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
                        Log.d("注册return",result.toString());
//                        Log.d("state",String.valueOf(state));
                        if (state == 0){
                            Intent intent = new Intent(SignUpActivity.this, LoginInActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Looper.prepare();
                            Toast.makeText(SignUpActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }else {
                        Log.d("sign",result.toString());
                        Looper.prepare();
                        Toast.makeText(SignUpActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * MD5加密
     * @param psw
     * @return
     */
    private String MD5(String psw){
        String result = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(psw.getBytes("UTF8"));
            byte s[] = digest.digest();
            for (int i = 0; i < s.length; i++) {
                result+=Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

}
