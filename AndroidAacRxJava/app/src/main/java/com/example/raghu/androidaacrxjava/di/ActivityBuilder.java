package com.example.raghu.androidaacrxjava.di;



import com.example.raghu.androidaacrxjava.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by raghu on 26/9/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
