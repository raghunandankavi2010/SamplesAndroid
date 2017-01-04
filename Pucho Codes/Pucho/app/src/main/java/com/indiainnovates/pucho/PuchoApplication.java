package com.indiainnovates.pucho;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.indiainnovates.pucho.dagger.components.ApplicationComponent;
import com.indiainnovates.pucho.dagger.components.DaggerApplicationComponent;
import com.indiainnovates.pucho.dagger.components.DaggerNetworkComponent;
import com.indiainnovates.pucho.dagger.components.NetworkComponent;
import com.indiainnovates.pucho.dagger.modules.ApplicationModule;
import com.indiainnovates.pucho.dagger.modules.NetworkModule;

import net.danlew.android.joda.JodaTimeAndroid;

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

        if (BuildConfig.DEBUG) {
            // do something for a debug build
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll() //for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate();
        JodaTimeAndroid.init(this);
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
