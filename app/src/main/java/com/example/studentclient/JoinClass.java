package com.example.studentclient;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JoinClass extends Activity {

    private ImageView back;
    private EditText code;
    private Button join;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_class);

        back = (ImageView) findViewById(R.id.back_form_join_class);
        code = (EditText)findViewById(R.id.text_join_code);
        join = (Button)findViewById(R.id.button_join_class);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinClass();
            }
        });
    }

    private void joinClass(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new FormBody.Builder()
                            .add("student_no",preferences.getString("id",""))
                            .add("token",preferences.getString("token",""))
                            .add("code",code.getText().toString())
                            .build();

                    final Request request = new Request.Builder()
                            .url(new URL(MyStaticValue.JOIN_CLASS))
                            .post(body)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("error","");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()){
                                String s = response.body().string();
                                    try{
                                        JSONObject result = new JSONObject(s);
                                        Log.d("success",request.toString());
                                        int state = result.getInt("state");
                                        if (state == 0){
                                            Looper.prepare();
                                            Toast.makeText(JoinClass.this,"加入课堂成功",Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                            }else {
                                Looper.prepare();
                                Toast.makeText(JoinClass.this,"验证码错误",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


}