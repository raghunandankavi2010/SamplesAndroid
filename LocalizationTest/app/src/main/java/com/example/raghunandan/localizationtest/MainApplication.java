package com.example.raghunandan.localizationtest;

import android.app.Application;

/**
 * Created by Raghunandan on 14-09-2016.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.onCreate(this, "en");
    }
}
