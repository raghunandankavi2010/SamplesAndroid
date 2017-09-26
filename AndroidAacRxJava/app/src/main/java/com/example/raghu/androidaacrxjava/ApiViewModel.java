package com.example.raghu.androidaacrxjava;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Pair;


import com.example.raghu.androidaacrxjava.models.Example;
import com.example.raghu.androidaacrxjava.repo.ApiLiveData;
import com.example.raghu.androidaacrxjava.repo.ApiRepository;

import javax.inject.Inject;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiViewModel extends ViewModel {


    private ApiRepository apiRepository;
    private MediatorLiveData<Pair<Example,Throwable>> data;
    @Inject
    ApiViewModel(ApiRepository apiRepository) {

        this.apiRepository = apiRepository;

    }

    public MediatorLiveData<Pair<Example,Throwable>> getData() {
        if (data == null) {
            data = new MediatorLiveData<>();
            loadData();
        }
        return data;
    }

    private void loadData() {
        data.addSource(apiRepository.getData(), new Observer<Pair<Example, Throwable>>() {
                    @Override
                    public void onChanged(@Nullable Pair<Example, Throwable> exampleThrowablePair) {
                        if(exampleThrowablePair!=null){
                                data.setValue(new Pair<Example, Throwable>(exampleThrowablePair.first,exampleThrowablePair.second));

                        }
                    }
                }
        );
    }


    }




