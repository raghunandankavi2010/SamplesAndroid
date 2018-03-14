package com.example.raghu.dagger2testandroid.data

import com.example.raghu.dagger2testandroid.api.Api
import com.example.raghu.dagger2testandroid.models.Example

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single
import retrofit2.Retrofit

/**
 * Created by raghu on 4/8/17.
 */
@Singleton
class MainModel @Inject
constructor( val retrofit: Retrofit) {

    /**\
     * Sample json hosted temporarily at https://api.myjson.com/bins/v6cg1
     * {"user":{"name":"Raghunandan Kavi","age":"30"}}
     */

    fun loadData(): Single<Example> {

        /**
         * use cache to continue network operation during configuration change.
         */
        val response = retrofit.create(Api::class.java).data.cache()
        return response
    }
}
