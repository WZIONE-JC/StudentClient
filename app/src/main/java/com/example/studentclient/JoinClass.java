package com.example.studentclient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
    private Handler handler;


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


        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        AlertDialog dialog = new AlertDialog.Builder(JoinClass.this)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("成功")
                                .setMessage("加入课堂成功！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getClassInfo();
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).create();
                        dialog.show();
                        break;
                }
            }
        };
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
                                            handler.sendEmptyMessage(0);
                                        }
                                    }catch (Exception e1){
                                        e1.printStackTrace();
                                    }
                            }else {
                                Log.d("fail",response.body().string());
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

    private void getClassInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("student_no",preferences.getString("id",""))
                            .add("token",preferences.getString("token",""))
                            .build();

                    Request request = new Request.Builder()
                            .url(new URL(MyStaticValue.GET_CLASS_INFO))
                            .post(body)
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()){
                                String result = response.body().string();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    Log.d("a",object.toString());
                                    int state = object.getInt("status");
                                    if (state == 0){
                                        JSONArray list = object.getJSONArray("list");
                                        for (int i = 0; i < list.length(); i++) {
                                            JSONObject temp = list.getJSONObject(i);
                                            List<Classroom> classrooms = LitePal.where("code = ? ",temp.getString("code")).find(Classroom.class);
                                            if (classrooms == null || classrooms.size() == 0){
                                            }else {
                                                for (int j = 0; j < classrooms.size(); j++) {
                                                    classrooms.get(j).delete();
                                                }
                                            }
                                            Classroom classroom = new Classroom();
                                            classroom.setCourseNo(temp.getInt("course_no"));
                                            classroom.setCourseName(temp.getString("course_name"));
                                            classroom.setTeachTime(Integer.valueOf(temp.getString("teach_time")));
                                            classroom.setTeachLocation(Integer.valueOf(temp.getString("teach_location")));
                                            classroom.setTeacherNo(temp.getString("teacher_no"));
                                            classroom.setCode(temp.getString("code"));
                                            classroom.save();
                                        }

                                    }
                                }catch (Exception e1){
                                    e1.printStackTrace();
                                }
                            }else {
                                Log.d("a",response.body().string());
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