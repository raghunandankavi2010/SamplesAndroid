package com.example.raghu.dagger2testandroid.presenter

import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.specbeemapassignment.models.Example
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * Created by raghu on 29/7/17.
 */

class MainActivityPresenter @Inject
constructor( var mainView: MainPresenterContract.View?,  val mainModel: MainModel) : MainPresenterContract.Presenter {

    override fun getWeatherData(lat: Double,lng:Double, APPID: String) {

        singlew = mainModel.weatherData(lat,lng,APPID)
        disposable.add(singlew!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeBy(// named arguments for lambda Subscribers
                        onSuccess = { example:com.example.raghu.specbeemapassignment.models.weather.Example ->mainView !!. showDataWeather (example) },
                        onError = { e: Throwable -> e.printStackTrace()}))

    }

    private val disposable = CompositeDisposable()
    private var  single : Single<Example>? =null
    private var  singlew : Single<com.example.raghu.specbeemapassignment.models.weather.Example>? =null


    override fun doSomething(query: String, key: String) {

        single = mainModel.loadData(query,key)
        disposable.add(single!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(// named arguments for lambda Subscribers
                        onSuccess = { example:Example ->mainView !!. showData (example) },
                        onError = { e: Throwable -> e.printStackTrace()}))



    }



    override fun unSubscribe() {
        mainView = null
        disposable.dispose()
    }

    companion object {
        private val TAG = MainActivityPresenter::class.java.simpleName
    }
}
