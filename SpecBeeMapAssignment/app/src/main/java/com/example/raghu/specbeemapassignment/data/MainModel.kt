package com.example.raghu.dagger2testandroid.data

import com.example.raghu.dagger2testandroid.api.Api
import com.example.raghu.specbeemapassignment.models.Example

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by raghu on 4/8/17.
 */
@Singleton
class MainModel @Inject
constructor( val retrofit: Retrofit,@Named("Weather") val retrofitW: Retrofit) {

    /**\
     * Sample json hosted temporarily at https://api.myjson.com/bins/v6cg1
     * {"user":{"name":"Raghunandan Kavi","age":"30"}}
     */

    fun loadData(query:String, key:String): Single<Example> {

        /**
         * use cache to continue network operation during configuration change.
         */
        val response = retrofit.create(Api::class.java).getData(query,key).cache()
        return response
    }

    fun weatherData(lat:Double, lng:Double,APPID:String): Single<com.example.raghu.specbeemapassignment.models.weather.Example> {

        /**
         * use cache to continue network operation during configuration change.
         */
        val response = retrofitW.create(Api::class.java).getWeather(lat,lng,"metric",APPID).cache()
        return response
    }
}
