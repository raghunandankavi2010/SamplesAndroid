package com.example.raghu.dagger2testandroid.dagger;

import android.app.Application;

import com.example.raghu.dagger2testandroid.DemoApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by raghu on 4/8/17.
 */

@Component(modules = {
        AndroidInjectionModule.class,
        MyApplicationModule.class,
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
