package com.example.raghu.databindingaac

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyViewModel(application: Application?) : AndroidViewModel(application!!) {
    //@JvmField
    var someText = MutableLiveData<String>()
    //@JvmField
    var error = MutableLiveData<String>()
    //@JvmField
    var someText2 = MutableLiveData<String>()
    //@JvmField
    var error2 = MutableLiveData<String>()
    fun printData() {
        Log.i("TAG", "" + someText.value)
    }
    

    fun check() {
        Log.i("TAG", "Called")
        if (TextUtils.isEmpty(someText.value)) {
            error.value = ("Edit Text is Empty")
        } else {
            error.value=("")
        }
        if (TextUtils.isEmpty(someText2.value)) {
            error2.value=("Edit Text 2is Empty")
        } else {
            error2.value=("")
        }
    }
}