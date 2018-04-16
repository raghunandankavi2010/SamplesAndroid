package com.example.raghu.dagger2testandroid.data

import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import com.example.raghu.dagger2testandroid.api.Api
import com.example.raghu.dagger2testandroid.models.Example
import com.example.raghu.dagger2testandroid.models.User

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Single
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception

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

    suspend fun getData():Result<Example>{

        try {
            val response = retrofit.create(Api::class.java).data_corountine
            val result = response.await()
            return Result.Success(result)
        } catch (e: HttpException) {
            // Catch http errors
          return Result.Error(e)
        } catch (e: Throwable) {
         return Result.Error(e)
        }
    }




    fun getData_user(uName: String):Result<Example> {
        SystemClock.sleep(5000)
        if(!TextUtils.isEmpty(uName))
        {
            val user = User()
            user.name = uName
            user.age = "30"
            val example = Example()
            example.user = user
            return  Result.Success(example)

        }else {
            return  Result.Error(Exception("Name cannot be empty"))
        }
    }
}
