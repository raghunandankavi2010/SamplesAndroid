package com.example.raghu.databindingaac;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;

import com.example.raghu.databindingaac.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final ViewModel model = ViewModelProviders.of(this).get(ViewModel.class);

        binding.setViewModel(model);

        binding.button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                model.printData();

            }
        });

        TextWatcherAdapter watcher = new TextWatcherAdapter() {
            @Override public void afterTextChanged(Editable s) {
                model.check();
            }
        };
        binding.editInput.addTextChangedListener(watcher);



    }


}
