package com.example.raghu.androidarchcomponentsrx.di;

import com.example.raghu.androidarchcomponentsrx.MainActivity;

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
