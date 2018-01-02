package com.ecommerce.ctdib2brecommerce.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ecommerce.ctdib2brecommerce.R

open class BaseActivtiy : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        if (resources.getBoolean(R.bool.isTablet)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onCreate(savedInstanceState)
    }
}
