package com.example.raghu.dagger2testandroid.presenter


import android.util.Log

import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.dagger2testandroid.models.Example
import com.example.raghu.dagger2testandroid.models.User
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.toSingle
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


/**
 * Created by raghu on 29/7/17.
 */

class MainActivityPresenter @Inject
constructor( var mainView: MainPresenterContract.View?,  val mainModel: MainModel) : MainPresenterContract.Presenter {
    private val disposable = CompositeDisposable()
    private var  single :Single<Example>? =null


    override fun doSomething() {

        single = mainModel.loadData()
        disposable.add(single!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(// named arguments for lambda Subscribers
                        onSuccess = { example:Example ->mainView !!. showData (example.user) },
                        onError = { e: Throwable -> e.printStackTrace()}))



    }



    override fun unSubscribe() {
        mainView = null
        //disposable.dispose()
    }

    companion object {
        private val TAG = MainActivityPresenter::class.java.simpleName
    }
}
