package com.example.raghu.dagger2android.dagger.components;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.raghu.dagger2android.dagger.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {



    Application provideApplication();
    SharedPreferences provideSharedPreferences();

}