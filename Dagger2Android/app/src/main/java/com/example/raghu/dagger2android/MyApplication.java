package com.example.raghu.dagger2android;

import android.app.Application;

import com.example.raghu.dagger2android.dagger.components.ApplicationComponent;
import com.example.raghu.dagger2android.dagger.components.DaggerApplicationComponent;
import com.example.raghu.dagger2android.dagger.components.DaggerNetworkComponent;
import com.example.raghu.dagger2android.dagger.components.NetworkComponent;
import com.example.raghu.dagger2android.dagger.modules.ApplicationModule;
import com.example.raghu.dagger2android.dagger.modules.NetworkModule;

/**
 * Created by raghu on 1/5/17.
 */

public class MyApplication extends Application {

    private static ApplicationComponent applicationComponent;

    private static NetworkComponent networkComponent;


    @Override
    public void onCreate() {

        super.onCreate();


        if (applicationComponent == null && networkComponent == null) {

            applicationComponent = DaggerApplicationComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .applicationModule(new ApplicationModule(this))
                    .build();

            networkComponent = DaggerNetworkComponent.builder()
                    .applicationComponent(applicationComponent)
                    .networkModule(new NetworkModule())
                    .build();
        }


    }

    public static ApplicationComponent component() {
        return applicationComponent;
    }

    public static NetworkComponent netcomponent() {
        return networkComponent;
    }

}
