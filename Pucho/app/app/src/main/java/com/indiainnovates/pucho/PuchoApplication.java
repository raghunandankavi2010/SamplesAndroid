package com.indiainnovates.pucho;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.indiainnovates.pucho.dagger.components.ApplicationComponent;
import com.indiainnovates.pucho.dagger.components.DaggerApplicationComponent;
import com.indiainnovates.pucho.dagger.components.DaggerNetworkComponent;
import com.indiainnovates.pucho.dagger.components.NetworkComponent;
import com.indiainnovates.pucho.dagger.modules.ApplicationModule;
import com.indiainnovates.pucho.dagger.modules.NetworkModule;

import io.fabric.sdk.android.Fabric;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Raghunandan on 14-12-2015.
 */
public class PuchoApplication extends Application {




    private static  ApplicationComponent applicationComponent;

    private static NetworkComponent networkComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

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
