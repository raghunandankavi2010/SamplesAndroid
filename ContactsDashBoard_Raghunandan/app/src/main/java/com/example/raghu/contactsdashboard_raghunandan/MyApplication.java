package com.example.raghu.contactsdashboard_raghunandan;

import android.app.Application;

/**
 * Created by raghu on 12/5/17.
 */

public class MyApplication extends Application {

    private static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        if(application==null)
        {
            application = this;
        }
    }

    public static MyApplication getInstance() {
        return application;
    }


}
