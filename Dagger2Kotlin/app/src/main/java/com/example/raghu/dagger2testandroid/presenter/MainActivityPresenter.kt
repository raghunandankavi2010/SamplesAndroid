package com.example.raghu.dagger2testandroid.presenter


import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.dagger2testandroid.data.Result
import com.example.raghu.dagger2testandroid.models.Example
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.util.function.BiConsumer
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext


/**
 * Created by raghu on 29/7/17.
 */

class MainActivityPresenter @Inject
constructor(var mainView: MainPresenterContract.View?, val mainModel: MainModel, val schedulerio: Scheduler, val mainThread:Scheduler ): MainPresenterContract.Presenter {



    private val disposable = CompositeDisposable()
    private var  single :Single<Example>? =null
    private val uiContext: CoroutineContext = UI
    private val bgContext: CoroutineContext = CommonPool


    override fun doSomething() {

        single = mainModel.loadData()
        disposable.add(single!!.subscribeOn(schedulerio)
                .observeOn(mainThread)
                .subscribeBy(// named arguments for lambda Subscribers
                        onSuccess = { example:Example ->mainView !!. showData (example.user,example.isExample) },
                        onError = { e: Throwable -> e.printStackTrace()}))



    }

    override fun getData() {
        launch(uiContext) {
            val task = async(bgContext) { mainModel.getData_user("Raghunandan Kavi") }
            val result = task.await() // non ui thread, suspend until finished
            if(result is Result.Success){
                var example = result.data
                mainView?.showData(example.user,example.isExample)
            }
        }
    }
    override fun getData_with_coroutines_retrofit() {
        launch(uiContext) {

            val result = mainModel.getData()
            if(result is Result.Success){
                var example = result.data
                mainView?.showData(example.user,example.isExample)
            }else if( result is Result.Error) {
                result.exception.printStackTrace()
            }
        }
    }

    override fun unSubscribe() {
        mainView = null
        //disposable.dispose()
    }

    companion object {
        private val TAG = MainActivityPresenter::class.java.simpleName
    }
}
