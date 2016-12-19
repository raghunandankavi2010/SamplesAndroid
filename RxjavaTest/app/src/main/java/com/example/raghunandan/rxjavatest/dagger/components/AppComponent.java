package com.example.raghunandan.rxjavatest.dagger.components;


import com.example.raghunandan.rxjavatest.MainActivity;
import com.example.raghunandan.rxjavatest.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);

}
