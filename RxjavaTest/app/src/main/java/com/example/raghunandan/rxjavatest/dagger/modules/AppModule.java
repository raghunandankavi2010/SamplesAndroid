package com.example.raghunandan.rxjavatest.dagger.modules;


import com.example.raghunandan.rxjavatest.TimerTest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    TimerTest provideTimerTest() {
        return new TimerTest();
    }

}
