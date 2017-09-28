package com.example.raghu.dagger2testandroid.dagger;

import com.example.raghu.dagger2testandroid.ui.main.MainActivity;
import com.example.raghu.dagger2testandroid.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by raghu on 4/8/17.
 */

@Module
public abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();


}