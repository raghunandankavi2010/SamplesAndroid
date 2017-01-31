package com.example.raghu.rxjavaziptest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by Raghu on 31-01-2017.
 */

public interface RequestInterface {

    @GET("android/jsonarray/")
    Observable<List<Android>> register();
}