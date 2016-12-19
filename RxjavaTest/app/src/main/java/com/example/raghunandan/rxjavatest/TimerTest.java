package com.example.raghunandan.rxjavatest;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Raghunandan on 18-12-2016.
 */

public class TimerTest {

    @Inject
    public TimerTest()
    {

    }


    public Observable<? extends Long> getObservable() {
        Observable timer= Observable.interval(0, 2, TimeUnit.SECONDS);
        timer.cache();
        return timer;
    }
}
