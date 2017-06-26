package com.example.raghu.androidarchitecturecomponentstest


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer

/**
 * Created by raghu on 26/6/17.
 */

class MainActivityViewModel : ViewModel() {

    val counterval = MutableLiveData<String>()

    fun startCounter() {

        object : CountDownTimer(10000, 1000) {
            override fun onTick(l: Long) {
                counterval.value = "seconds remaining: " + l / 1000
            }

            override fun onFinish() {
                counterval.value = "finished"
            }
        }.start()


    }

}
