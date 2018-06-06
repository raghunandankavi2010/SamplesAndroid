package com.example.raghu.androidaacrxjava;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.example.raghu.androidaacrxjava.models.Example;
import com.example.raghu.androidaacrxjava.repo.ApiRepository;

import javax.inject.Inject;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiViewModel extends ViewModel {


    private ApiRepository apiRepository;
    public MediatorLiveData<Pair<Example, Throwable>> data;
    public MediatorLiveData<Boolean> check = new MediatorLiveData<Boolean>();

    @Inject
    ApiViewModel(ApiRepository apiRepository) {

        this.apiRepository = apiRepository;

    }

    public MediatorLiveData<Boolean> getCheck() {
        return check;
    }

    public MediatorLiveData<Pair<Example, Throwable>> getData() {
        if (data == null) {
            check.setValue(true);
            data = new MediatorLiveData<>();
            loadData();
        }
        return data;
    }

    private void loadData() {
        data.addSource(apiRepository.getData(), new Observer<Pair<Example, Throwable>>() {
                    @Override
                    public void onChanged(@Nullable Pair<Example, Throwable> exampleThrowablePair) {
                        if (exampleThrowablePair != null) {
                            check.setValue(false);
                            data.setValue(exampleThrowablePair);//new Pair<Example, Throwable>(exampleThrowablePair.first,exampleThrowablePair.second));

                        } else {
                            check.setValue(false);
                        }
                    }
                }
        );
    }

}




