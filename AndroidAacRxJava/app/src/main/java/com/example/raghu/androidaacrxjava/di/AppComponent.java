package com.example.raghu.androidaacrxjava.di;

import android.app.Application;


import com.example.raghu.androidaacrxjava.DemoApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by raghu on 26/9/17.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(DemoApplication app);
}
