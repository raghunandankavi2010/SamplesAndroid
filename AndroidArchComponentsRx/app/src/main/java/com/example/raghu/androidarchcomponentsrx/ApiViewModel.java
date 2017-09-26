package com.example.raghu.androidarchcomponentsrx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.repo.ApiRepository;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import javax.inject.Inject;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiViewModel extends ViewModel {


    private ApiRepository apiRepository;
    private MediatorLiveData<Resource<Example>> data;

    @Inject
    ApiViewModel(ApiRepository apiRepository) {

        this.apiRepository = apiRepository;
    }


    public MediatorLiveData<Resource<Example>> getData() {
        if (data == null) {
            data = new MediatorLiveData<>();
            loadData();
        }
        return data;
    }

    private void loadData() {
        data.addSource(apiRepository.getData(), new Observer<Resource<Example>>() {

                    @Override
                    public void onChanged(@Nullable Resource<Example> exampleResource) {
                        if(exampleResource!=null){
                            data.setValue(exampleResource);
                        }
                    }
                }
        );
    }





}
