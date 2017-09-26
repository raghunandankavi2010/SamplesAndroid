package com.example.raghu.androidarchcomponentsrx.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.example.raghu.androidarchcomponentsrx.Api;
import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by raghu on 26/9/17.
 */

@Singleton
public class ApiRepository {

    private Retrofit retrofit;
    private ApiLiveData apiLiveData = new ApiLiveData();

    @Inject
    public ApiRepository(Retrofit retrofit) {

        this.retrofit = retrofit;

    }

    public MediatorLiveData<Resource<Example>> getData(){

      Single<Response<Example>> exampleSingle =  retrofit.create(Api.class).getData();
      exampleSingle.cache();
      apiLiveData.getData(exampleSingle);

      return apiLiveData;
    }
}
