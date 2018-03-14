package com.example.raghu.dagger2testandroid.api;

import com.example.raghu.dagger2testandroid.models.Example;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;


/**
 * Created by raghu on 5/8/17.
 */

public interface Api {

    @Headers("Content-Type: application/json")
    @GET("bins/v6cg1")
    Single<Example> getData();
}
