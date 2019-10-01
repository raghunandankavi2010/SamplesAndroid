package com.example.raghu.dagger2testandroid.api

import com.example.raghu.dagger2testandroid.models.User


import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers


/**
 * Created by raghu on 5/8/17.
 */

interface Api {

    @get:Headers("Content-Type: application/json")
    @get:GET("bins/v6cg1")
    val data: Single<User>

    @Headers("Content-Type: application/json")
    @GET("bins/ofo69")
    suspend fun data_coroutines(): User

}
