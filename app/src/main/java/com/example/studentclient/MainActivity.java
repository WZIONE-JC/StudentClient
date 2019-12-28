package com.example.studentclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private TextView topText;
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LitePal.getDatabase();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        navigationView = (BottomNavigationView)findViewById(R.id.bottom_menu);
        viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        topText = (TextView) findViewById(R.id.top_text);


        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new TalkFragment());
        adapter.addFragment(new PersonalFragment());
        viewPager.setAdapter(adapter);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        getClassInfo();
        initData();
        /**
         * TODO change view
         */
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.talk_area:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.personal_info:
                        viewPager.setCurrentItem(2);
                        break;
                }

                return false;
            }
        });


        /**
         * TODO do something when page changed
         */
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
                topText.setText(navigationView.getMenu().getItem(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_SDCARD_PERMISSION_REQUEST_CODE);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tool_bar_menu,menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }




    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("user_id",preferences.getString("id",""))
                            .add("token",preferences.getString("token",""))
                            .build();

                    Request request = new Request.Builder()
                            .url(new URL(MyStaticValue.GET_INFO_PATH))
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
                                    int status = object.getInt("state");
                                    if (status == 0){
                                        JSONObject student = object.getJSONObject("student");
                                        editor.putString("name",student.getString("student_name"));
                                        editor.putString("sex",student.getString("sex"));
                                        editor.putString("school",student.getString("school"));
                                        editor.putString("major",student.getString("major"));
                                        editor.apply();
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
