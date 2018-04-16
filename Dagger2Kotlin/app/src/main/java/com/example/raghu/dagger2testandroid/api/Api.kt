package com.example.raghu.dagger2testandroid.api

import com.example.raghu.dagger2testandroid.models.Example


import io.reactivex.Single
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers


/**
 * Created by raghu on 5/8/17.
 */

interface Api {

    @get:Headers("Content-Type: application/json")
    @get:GET("bins/v6cg1")
    val data: Single<Example>

    @get:Headers("Content-Type: application/json")
    @get:GET("bins/ciahj")
    val data_corountine: Deferred<Example>
}
