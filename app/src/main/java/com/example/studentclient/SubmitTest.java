package com.example.studentclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubmitTest extends AppCompatActivity implements View.OnClickListener{
    private TextView text_test_title;
    private TextView text_test_detail;
    private TextView text_test_duetime;
    private Button btn_upload;
    private Button btn_submit;
    private ImageView img_back;
    private int testNo;
    private Handler handler;
    private Uri fileUri;
    private String path;
    private TextView fileName;
    private SharedPreferences preferences;
    private String courseNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_test);
        initview();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        File file = new File(path);
                        fileName.setText(file.getName());
                        break;
                    case 1:
                        Toast.makeText(SubmitTest.this,"提交成功",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        };
    }

    private void initview() {
        btn_upload=findViewById(R.id.upload_file_test);
        btn_submit=findViewById(R.id.submit_answer_test);
        text_test_title =(TextView)findViewById(R.id.my_test_title);
        text_test_detail =(TextView)findViewById(R.id.my_test_detail);
        text_test_duetime =(TextView)findViewById(R.id.my_test_deadtime);
        img_back=findViewById(R.id.back);

        btn_upload.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        img_back.setOnClickListener(this);
        testNo = getIntent().getIntExtra("testNo",-1);
        List<TestTable> tests = LitePal.where("testNo = ? ", String.valueOf(testNo)).find(TestTable.class);
        text_test_title.setText(tests.get(0).getTitle());
        text_test_duetime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tests.get(0).getDeadline()));
        text_test_detail.setText(tests.get(0).getContent());
        courseNo = tests.get(0).getCourseNo();
        fileName = (TextView)findViewById(R.id.my_test_answer);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.upload_file_test://上传
                chooseFile();
                break;
            case R.id.submit_answer_test://提交
                uploadFile();
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (data == null)
                return;
            fileUri = data.getData();
            path = MyStaticValue.getFilePathByUri(this,fileUri);
            handler.sendEmptyMessage(0);
        }
    }

    private void chooseFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);
    }

    private void uploadFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    File file = new File(path);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("*/*"),file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
//                            .addFormDataPart("user_no",preferences.getString("id",""))
                            .addFormDataPart("course_no",courseNo)
                            .addFormDataPart("test_no",String.valueOf(testNo))
                            .addFormDataPart("student_no",preferences.getString("id",""))
                            .addFormDataPart("token",preferences.getString("token",""))
                            .addFormDataPart("content",file.getName(),fileBody)
                            .build();

                    Request request = new Request.Builder()
                            .url(new URL(MyStaticValue.UPLOAD_TEST))
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.i("lfq" ,e.toString());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String result = response.body().string();
                                try {
                                    JSONObject object = new JSONObject(result);
                                    int state = object.getInt("state");
                                    if (state == 0){
                                        handler.sendEmptyMessage(1);
                                    }
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }

                            } else {
                                Log.d("失败",response.body().string());
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
