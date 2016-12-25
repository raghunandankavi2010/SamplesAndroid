package com.example.raghunandan.rxjavatest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.raghunandan.rxjavatest.dagger.DaggerInjector;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView textView;
    @Inject
    MainPresenter mainPresenter;

    private int initialvalue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaggerInjector.get().inject(this);
        mainPresenter.setCallBack(this);

        textView = (TextView) findViewById(R.id.text);

        if(savedInstanceState!=null)
        {
            initialvalue = savedInstanceState.getInt("value");
            mainPresenter.doSomeWork(initialvalue);
            Log.i("InitialValue", ""+initialvalue);
        }else {
            mainPresenter.doSomeWork(initialvalue);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("value",initialvalue);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.clear();
    }


    @Override
    public void callBack(Long timer) {
        textView.setText(String.valueOf(timer));
        initialvalue = timer.intValue();
    }


}