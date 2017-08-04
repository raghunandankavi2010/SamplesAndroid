package com.example.raghu.dagger2android.dagger.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.raghu.dagger2android.MainActivity;
import com.example.raghu.dagger2android.dagger.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(Application appplication);

    Application provideApplication();
    SharedPreferences provideSharedPreferences();


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder app(Application application);

        ApplicationComponent build();

    }

}