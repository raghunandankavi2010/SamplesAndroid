package com.example.raghu.androidarchcomponentsrx;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;
import com.example.raghu.androidarchcomponentsrx.vo.Status;

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

        apiViewModel.getData().observe(this, new Observer<Resource<Example>>() {
            @Override
            public void onChanged(@Nullable Resource<Example> exampleResource) {

                if(exampleResource!=null) {
                    if (exampleResource.status == Status.LOADING) {

                        Log.i("Tag","Loading");
                    }else if (exampleResource.status == Status.ERROR)  {

                        Log.i("Tag","Error");
                    }
                    else {
                        Example example = exampleResource.data;
                        tv.setText(example.getUser().getName());
                    }
                }

            }
        });
    }


}
