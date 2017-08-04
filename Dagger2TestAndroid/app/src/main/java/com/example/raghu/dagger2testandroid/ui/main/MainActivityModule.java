package com.example.raghu.dagger2testandroid.ui.main;

import com.example.raghu.dagger2testandroid.data.MainModel;
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter;
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mertsimsek on 25/05/2017.
 */
@Module
public class MainActivityModule {

    @Provides
    MainPresenterContract.View provideMainView(MainActivity mainActivity){
        return mainActivity;
    }

    @Provides
    MainActivityPresenter provideMainPresenter(MainPresenterContract.View  mainView, MainModel mainModel){
        return new MainActivityPresenter(mainView, mainModel);
    }
}
