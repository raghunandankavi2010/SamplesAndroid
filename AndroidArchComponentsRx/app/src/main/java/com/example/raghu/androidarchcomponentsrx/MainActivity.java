package com.example.raghu.androidarchcomponentsrx;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.raghu.androidarchcomponentsrx.databinding.ActivityMainBinding;
import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private TextView tv;
    private ProgressBar pb;

    private ApiViewModel apiViewModel;
    private ActivityMainBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        tv = findViewById(R.id.name);

        pb = findViewById(R.id.progressBar);

        apiViewModel = ViewModelProviders.of(this, viewModelFactory).get(ApiViewModel.class);

        apiViewModel.getData().observe(this, new Observer<Resource<Example>>() {
            @Override
            public void onChanged(@Nullable Resource<Example> exampleResource) {

                binding.setExample(exampleResource.data);

                binding.setResource(exampleResource);


           /*     if(exampleResource!=null) {
                    if (exampleResource.status == Status.LOADING) {

                        pb.setVisibility(View.VISIBLE);
                        Log.i("Tag","Loading");
                    }else if (exampleResource.status == Status.ERROR)  {
                        pb.setVisibility(View.GONE);
                        tv.setText(exampleResource.message);
                        Log.i("Tag","Error");
                    }
                    else {
                        pb.setVisibility(View.GONE);
                        Example example = exampleResource.data;
                        tv.setText(example.getUser().getName());
                    }
                }*/

            }
        });
    }


}
