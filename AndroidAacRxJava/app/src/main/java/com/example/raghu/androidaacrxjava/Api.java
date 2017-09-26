package com.example.raghu.androidaacrxjava;



import com.example.raghu.androidaacrxjava.models.Example;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by raghu on 26/9/17.
 */

public interface Api {

    //https://api.myjson.com/bins/1apdwp
    @Headers("Content-Type: application/json")
    @GET("bins/1apdwp")
   Single<Response<Example>> getData();
}
