package com.example.raghunandan.rxjavatest.dagger;


import com.example.raghunandan.rxjavatest.dagger.components.AppComponent;
import com.example.raghunandan.rxjavatest.dagger.components.DaggerAppComponent;
import com.example.raghunandan.rxjavatest.dagger.modules.AppModule;

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder()
             .appModule(new AppModule())
             .build();

    public static AppComponent get() {
        return appComponent;
    }


}
