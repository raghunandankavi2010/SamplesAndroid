package com.example.raghu.dagger2testandroid.dagger;

import android.app.Activity;


import com.example.raghu.dagger2testandroid.ui.main.MainActivity;
import com.example.raghu.dagger2testandroid.ui.main.MainActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by raghu on 4/8/17.
 */

@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);


}