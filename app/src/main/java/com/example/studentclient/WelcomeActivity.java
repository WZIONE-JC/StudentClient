package com.example.studentclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                boolean isLogin = preferences.getBoolean("isLogin",false);
                boolean isLogin = true;
                if (!isLogin){
                    //go to login activity
                    startActivity(new Intent(WelcomeActivity.this,LoginInActivity.class));
                }else {
                    //go to main activity
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }

                finish();//destroy this activity
            }
        },1000);

    }
}
