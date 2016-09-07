package com.india.innovates.pucho.dagger.modules;

import android.content.SharedPreferences;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class SharedPreferencesGet {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesGet( SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
