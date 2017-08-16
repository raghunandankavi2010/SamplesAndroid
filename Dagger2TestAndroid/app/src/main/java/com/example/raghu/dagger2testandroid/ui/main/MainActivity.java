package com.example.raghu.dagger2testandroid.ui.main;

import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.raghu.dagger2testandroid.R;
import com.example.raghu.dagger2testandroid.models.User;
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter;
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class MainActivity extends AppCompatActivity implements MainPresenterContract.View{

    CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");

    @Inject
    MainActivityPresenter mainPresenter;

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =  findViewById(R.id.tv);
        button =  findViewById(R.id.button);

        if(savedInstanceState!=null) {
            button.setEnabled(false);
            textView.setText(savedInstanceState.getString("name"));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.doSomething();
                espressoTestIdlingResource.increment();
            }
        });



    }



    @Override
    public void showData(User user){
        espressoTestIdlingResource.decrement();
        button.setEnabled(false);
        textView.setText(user.getName());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name",textView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.unSubscribe();
    }
}
