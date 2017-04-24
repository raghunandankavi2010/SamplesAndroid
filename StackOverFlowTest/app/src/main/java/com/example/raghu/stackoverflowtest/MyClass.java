package com.example.raghu.stackoverflowtest;

import android.app.Application;

/**
 * Created by raghu on 24/4/17.
 */

public class MyClass extends Application {

    // Singleton instance
    private static MyClass sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Setup singleton instance
        sInstance = this;
    }

    // Getter to access Singleton instance
    public static MyClass getInstance() {
        return sInstance ;
    }
}