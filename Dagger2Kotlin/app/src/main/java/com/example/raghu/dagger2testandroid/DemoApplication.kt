package com.example.raghu.dagger2testandroid

import android.app.Application
import com.example.raghu.dagger2testandroid.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by raghu on 4/8/17.
 */

class DemoApplication : Application(),HasAndroidInjector {
    override fun androidInjector(): AndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }

@Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

    }


}