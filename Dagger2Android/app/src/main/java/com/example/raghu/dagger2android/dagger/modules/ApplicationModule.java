package com.example.raghu.dagger2android.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;



import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raghunandan on 14-12-2015.
 */
@Module
public class ApplicationModule {

    private Application app;
    private String PREF_NAME = "prefs";



    public ApplicationModule(Application app) {
        this.app = app;
    }



    @Singleton
    @Provides
    public Application provideApplication() {
        return app;
    }

    @Singleton
    @Provides
    public SharedPreferences getAppPreferences() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


}