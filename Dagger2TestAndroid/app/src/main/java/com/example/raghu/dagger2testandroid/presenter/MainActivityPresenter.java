package com.example.raghu.dagger2testandroid.presenter;


import android.util.Log;

import com.example.raghu.dagger2testandroid.data.MainModel;
import com.example.raghu.dagger2testandroid.models.Example;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by raghu on 29/7/17.
 */

public class MainActivityPresenter implements MainPresenterContract.Presenter {

    private MainPresenterContract.View mainView;
    private MainModel mainModel;
    private CompositeDisposable disposable = new CompositeDisposable();
    private static final String TAG = MainActivityPresenter.class.getSimpleName();


    @Inject
    public MainActivityPresenter(MainPresenterContract.View view, MainModel mainModel) {

        this.mainView =view;
        this.mainModel = mainModel;
    }


    @Override
    public void doSomething() {

        Single<Example> observable =  mainModel.loadData();

        disposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Example>() {
                               @Override
                               public void onSuccess(Example example) {
                                   mainView.showData(example.getUser());
                               }

                               @Override
                               public void onError(Throwable e) {
                                  e.printStackTrace();
                               }
                           }));



    }


    @Override
    public void unSubscribe() {
        mainView = null;
        disposable.dispose();
    }
}
