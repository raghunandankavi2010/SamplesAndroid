package com.example.raghu.dagger2testandroid.api


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
    fun getData(@Query("query") query:String,@Query("key") key:String) : Single<com.example.raghu.specbeemapassignment.models.Example>


    @Headers("Content-Type: application/json")
    @GET("data/2.5/weather")
    fun getWeather(@Query("lat") lat:Double,@Query("lon") lng:Double,@Query("units") units:String,@Query("APPID") key:String) : Single<com.example.raghu.specbeemapassignment.models.weather.Example>

}
