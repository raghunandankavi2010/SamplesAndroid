package com.ladyspyd.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.ladyspyd.R;


public class LSSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lssplash);


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {


            }
        }, secondsDelayed * 1000);
    }
}
