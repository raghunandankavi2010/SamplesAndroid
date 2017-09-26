package com.example.raghu.androidaacrxjava;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.TextView;

import com.example.raghu.androidaacrxjava.models.Example;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private TextView tv;

    private ApiViewModel apiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.name);

        apiViewModel = ViewModelProviders.of(this, viewModelFactory).get(ApiViewModel.class);

        apiViewModel.getData().observe(this, new Observer<Pair<Example, Throwable>>() {

            @Override
            public void onChanged(@Nullable Pair<Example, Throwable> exampleThrowablePair) {

                if(exampleThrowablePair.first!=null){
                    tv.setText(exampleThrowablePair.first.getUser().getName());
                }else{
                    exampleThrowablePair.second.printStackTrace();
                }
            }
        });


    }


}
