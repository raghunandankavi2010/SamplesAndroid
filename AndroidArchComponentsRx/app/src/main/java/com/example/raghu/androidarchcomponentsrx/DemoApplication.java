package com.example.raghu.androidarchcomponentsrx;

import android.app.Activity;
import android.app.Application;


import com.example.raghu.androidarchcomponentsrx.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by raghu on 26/9/17.
 */

public class DemoApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}