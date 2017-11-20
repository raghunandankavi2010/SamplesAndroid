package com.example.raghu.dagger2testandroid.presenter

import com.example.raghu.specbeemapassignment.models.Example

/**
 * Created by raghu on 29/7/17.
 */

interface MainPresenterContract {


    interface View {

        fun showData(example: Example)
        fun showDataWeather(example: com.example.raghu.specbeemapassignment.models.weather.Example)


    }

    interface Presenter {

        fun doSomething(query:String,key:String)
        fun getWeatherData(lat:Double,lng:Double,APPID:String)

        fun unSubscribe()
    }
}

