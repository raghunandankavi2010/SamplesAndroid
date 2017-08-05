package com.example.raghu.dagger2testandroid.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.raghu.dagger2testandroid.R;
import com.example.raghu.dagger2testandroid.models.User;
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter;
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class MainActivity extends AppCompatActivity implements MainPresenterContract.View{

    @Inject
    MainActivityPresenter mainPresenter;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        mainPresenter.doSomething();


    }



    @Override
    public void showData(User user){
        textView.setText(user.getName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unSubscribe();
    }
}
