package com.example.raghu.androidaacrxjava.repo;



import android.util.Pair;

import com.example.raghu.androidaacrxjava.Api;
import com.example.raghu.androidaacrxjava.models.Example;

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
    private ApiLiveData<Pair<Example,Throwable>> apiLiveData = new ApiLiveData();

    @Inject
    public ApiRepository(Retrofit retrofit) {

        this.retrofit = retrofit;

    }

    public ApiLiveData<Pair<Example,Throwable>> getData(){

      Single<Response<Example>> exampleSingle =  retrofit.create(Api.class).getData();
      exampleSingle.cache();
      apiLiveData.getData(exampleSingle);

      return apiLiveData;
    }
}
