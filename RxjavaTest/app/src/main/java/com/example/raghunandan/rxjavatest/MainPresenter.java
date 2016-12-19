package com.example.raghunandan.rxjavatest;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Raghunandan on 18-12-2016.
 */

public class MainPresenter {


    private final CompositeDisposable disposables = new CompositeDisposable();

    private static final String TAG = MainPresenter.class.getSimpleName();

    private TimerTest timerTest;
    private Callback callback;

    @Inject
    public MainPresenter(TimerTest timerTest) {
        this.timerTest = timerTest;
    }

    public void doSomeWork() {

        disposables.add(timerTest.getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));
    }




    private DisposableObserver<Long> getObserver() {
        return new DisposableObserver<Long>() {

            @Override
            public void onNext(Long value) {

                callback.callBack(value);
                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onComplete() {
                Log.d(TAG, " onComplete");
            }
        };
    }

    public void clear()
    {
        callback = null;
        disposables.clear();
    }

    public void setCallBack(MainActivity callBack) {
        this.callback = callBack;
    }
}
