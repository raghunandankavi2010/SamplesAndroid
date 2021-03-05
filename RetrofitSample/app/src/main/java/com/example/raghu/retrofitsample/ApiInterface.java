package com.example.raghu.retrofitsample;

/**
 * Created by raghu on 23/4/17.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {
    @Headers("User-Agent: Fooducate - Android - Version 1.0")
    @GET("/api/v0/product/01223004")
    Call<ResponseObject> getProducts();
}
