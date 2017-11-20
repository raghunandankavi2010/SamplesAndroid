package com.example.raghu.dagger2testandroid.api


import com.example.raghu.specbeemapassignment.models.Example
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by raghu on 8/11/17.
 */

interface Api {

   // ?query=123+main+street&key=AIzaSyAZFybetiE-98o4Ky3pUlojqHbo4W9FjsM
    @Headers("Content-Type: application/json")
    @GET("maps/api/place/textsearch/json")
    fun getData(@Query("query") query:String,@Query("key") key:String) : Single<Example>

}
