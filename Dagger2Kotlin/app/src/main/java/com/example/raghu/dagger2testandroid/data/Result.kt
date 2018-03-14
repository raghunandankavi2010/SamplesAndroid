package com.example.raghu.dagger2testandroid.data


/**
 * Created by raghu on 14/3/18.
 */

sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()
}