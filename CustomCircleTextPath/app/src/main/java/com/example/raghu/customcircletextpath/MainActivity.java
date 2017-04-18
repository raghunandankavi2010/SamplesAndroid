package com.example.raghu.customcircletextpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomCircleView customCircleView = new CustomCircleView(this);
        setContentView(customCircleView);
    }
}
