package com.example.raghu.androidarchcomponentsrx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.repo.AbsentLiveData;
import com.example.raghu.androidarchcomponentsrx.repo.ApiRepository;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import javax.inject.Inject;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiViewModel extends ViewModel {


    private ApiRepository apiRepository;
    private LiveData<Resource<Example>> data ;


    @Inject
    ApiViewModel(ApiRepository apiRepository) {

        this.apiRepository = apiRepository;



    }

    @VisibleForTesting
    public LiveData<Resource<Example>> getData() {


        data =apiRepository.getData();
        return data;

    }

   /* private void loadData() {
        data = apiRepository.getData();

        data.addSource(apiRepository.getData(), new Observer<Resource<Example>>() {

                    @Override
                    public void onChanged(@Nullable Resource<Example> exampleResource) {
                        if(exampleResource!=null){
                            data.setValue(exampleResource);
                        }
                    }
                }
        );
    }*/



}
