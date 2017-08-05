package com.example.raghu.dagger2testandroid.data;

import com.example.raghu.dagger2testandroid.api.Api;
import com.example.raghu.dagger2testandroid.models.Example;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by raghu on 4/8/17.
 */
@Singleton
public class MainModel {

    private Retrofit retrofit;

    @Inject
    public MainModel(Retrofit retrofit) {

        this.retrofit = retrofit;
    }

    public Single loadData(){

        Single<Example> response = retrofit.create(Api.class).getData();
        return response;
    }
}
