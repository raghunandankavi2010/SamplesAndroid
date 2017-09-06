package com.example.raghu.circlemenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var customView : CustomView = CustomView(this)
        setContentView(customView)
    }
}
