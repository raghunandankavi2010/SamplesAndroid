package com.example.raghu.dagger2testandroid.ui.main

import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mertsimsek on 25/05/2017.
 */
@Module
class MainActivityModule {

    @Provides
    internal fun provideMainView(mainActivity: MainActivity): MainPresenterContract.View {
        return mainActivity
    }

    @Provides
    internal fun provideMainPresenter(mainView: MainPresenterContract.View, mainModel: MainModel): MainActivityPresenter {
        return MainActivityPresenter(mainView, mainModel, Schedulers.io(), AndroidSchedulers.mainThread())
    }
}
