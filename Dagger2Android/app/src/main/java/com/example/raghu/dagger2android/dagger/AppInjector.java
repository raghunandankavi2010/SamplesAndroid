package com.example.raghu.dagger2android.dagger;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.raghu.dagger2android.MyApplication;
import com.example.raghu.dagger2android.dagger.components.ApplicationComponent;
import com.example.raghu.dagger2android.dagger.components.DaggerApplicationComponent;
import com.example.raghu.dagger2android.dagger.components.DaggerNetworkComponent;
import com.example.raghu.dagger2android.dagger.components.NetworkComponent;
import com.example.raghu.dagger2android.dagger.modules.ApplicationModule;
import com.example.raghu.dagger2android.dagger.modules.NetworkModule;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Helper class to automatically inject fragments if they implement {@link Injectable}.
 */
public class AppInjector {

    private static ApplicationComponent applicationComponent;
    private static NetworkComponent networkComponent;
    private AppInjector() {}


    public static void init(MyApplication application) {


        if(applicationComponent==null && networkComponent == null)
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(application))
                .build();
        applicationComponent.inject(application);

        networkComponent = DaggerNetworkComponent.builder()
                .applicationComponent(applicationComponent)
                .networkModule(new NetworkModule())
                .build();

        application
                .registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        handleActivity(activity);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {

                    }

                    @Override
                    public void onActivityResumed(Activity activity) {

                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof HasSupportFragmentInjector) {
            AndroidInjection.inject(activity);
        }
      /*  if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(FragmentManager fm, Fragment f,
                                        Bundle savedInstanceState) {
                                    if (f instanceof Injectable) {
                                        AndroidSupportInjection.inject(f);
                                    }
                                }
                            }, true);
        }*/
    }
}
