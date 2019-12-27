package com.example.raghu.databindingaac

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel

class MyViewModel(application: Application?) : AndroidViewModel(application!!) {
    @JvmField
    var someText = BindableString()
    @JvmField
    var error = BindableString()
    @JvmField
    var someText2 = BindableString()
    @JvmField
    var error2 = BindableString()
    fun printData() {
        Log.i("TAG", "" + someText.get())
    }

    fun reset() {
        error.set(null)
        someText.set(null)
    }

    fun check() {
        if (TextUtils.isEmpty(someText.get())) {
            error.set("Edit Text is Empty")
        } else {
            error.set("")
        }
        if (TextUtils.isEmpty(someText2.get())) {
            error2.set("Edit Text 2is Empty")
        } else {
            error2.set("")
        }
    }
}