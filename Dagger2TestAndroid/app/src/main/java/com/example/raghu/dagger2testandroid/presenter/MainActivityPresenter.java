package com.example.raghu.dagger2testandroid.presenter;


import com.example.raghu.dagger2testandroid.data.MainModel;

import javax.inject.Inject;


/**
 * Created by raghu on 29/7/17.
 */

public class MainActivityPresenter implements MainPresenterContract.Presenter {

    private MainPresenterContract.View mainView;
    private MainModel mainModel;



    @Inject
    public MainActivityPresenter(MainPresenterContract.View view, MainModel mainModel) {

        this.mainView =view;
        this.mainModel = mainModel;
    }


    @Override
    public void doSomething() {

        String date = mainModel.loadData();
    }
}
