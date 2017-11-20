package com.example.raghu.specbeemapassignment.dagger

import com.example.raghu.dagger2testandroid.data.MainModel
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract
import com.example.raghu.specbeemapassignment.MainActivity

import dagger.Module
import dagger.Provides

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
        return MainActivityPresenter(mainView, mainModel)
    }
}
