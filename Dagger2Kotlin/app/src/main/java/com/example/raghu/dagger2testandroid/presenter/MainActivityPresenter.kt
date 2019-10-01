package com.example.raghu.dagger2testandroid.presenter


import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.dagger2testandroid.data.Result
import com.example.raghu.dagger2testandroid.models.User
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject


/**
 * Created by raghu on 29/7/17.
 */

class MainActivityPresenter @Inject
constructor(var mainView: MainPresenterContract.View?, val mainModel: MainModel, val schedulerio: Scheduler, val mainThread:Scheduler ): MainPresenterContract.Presenter {



    private val disposable = CompositeDisposable()
    private var  single :Single<User>? =null
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var job: Job? = null



    override fun doSomething() {

        single = mainModel.loadData()
        disposable.add(single!!.subscribeOn(schedulerio)
                .observeOn(mainThread)
                .subscribe(
                        { user:User -> mainView !!. showData (user) },
                        { throwable:Throwable -> throwable.printStackTrace()}
                ))
                /*.subscribeBy(// named arguments for lambda Subscribers
                        onSuccess = { example:Example ->mainView !!. showData (example.user,example.isExample) },
                        onError = { e: Throwable -> e.printStackTrace()}))*/



    }

    override fun getData() {
        job = uiScope.launch{
            val task = async(Dispatchers.IO) { mainModel.getData_user("Raghunandan Kavi") }
            val result = task.await() // non ui thread, suspend until finished
            if(result is Result.Success){
                var user = result.data
                mainView?.showData(user)
            }
        }
    }
    override fun getData_with_coroutines_retrofit() {
        job = uiScope.launch {

            val result = mainModel.getData()
            if(result is Result.Success){
                var user = result.data
                mainView?.showData(user)
            }else if( result is Result.Error) {
                result.exception.printStackTrace()
            }
        }
    }

    override fun unSubscribe() {
        mainView = null
        job?.cancel()
        //disposable.dispose()
    }

    companion object {
        private val TAG = MainActivityPresenter::class.java.simpleName
    }
}
