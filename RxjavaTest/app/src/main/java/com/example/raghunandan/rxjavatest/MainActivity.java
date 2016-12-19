package com.example.raghunandan.rxjavatest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.raghunandan.rxjavatest.dagger.DaggerInjector;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btn;
    TextView textView;
    @Inject
    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.get().inject(this);
        mainPresenter.setCallBack(this);
        btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.text);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainPresenter.doSomeWork();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.clear();
    }


    @Override
    public void callBack(Long timer) {

        textView.setText(String.valueOf(timer));
    }
}