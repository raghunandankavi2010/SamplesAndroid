package com.example.raghu.androidaacrxjava.repo;

import android.arch.lifecycle.MediatorLiveData;
import android.util.Pair;

import com.example.raghu.androidaacrxjava.models.Example;


import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiLiveData<T> extends MediatorLiveData<Pair<Example,Throwable>> {

    Disposable disposable;


    public void getData(Single<Response<Example>> apicall){


        disposable = apicall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Response<Example>, Throwable>() {
                    @Override
                    public void accept(Response<Example> exampleResponse, Throwable throwable) throws Exception {


                        if(exampleResponse.isSuccessful()) {

                            setValue(new Pair<Example, Throwable>(exampleResponse.body(),throwable));
                        } else {
                            setValue(new Pair<Example, Throwable>(exampleResponse.body(),throwable));
                        }


                    }
                });


    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(disposable!=null)
            disposable.dispose();
    }




}