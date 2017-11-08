package com.example.raghu.dagger2testandroid.api

import com.example.raghu.dagger2testandroid.models.Example
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by raghu on 8/11/17.
 */

interface Api {

    @Headers("Content-Type: application/json")
    @GET("bins/sm3jj")
    fun getData() : Single<Example>

}
