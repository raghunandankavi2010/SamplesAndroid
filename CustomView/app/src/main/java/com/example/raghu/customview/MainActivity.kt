package com.example.raghu.customview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customView = CustomView(this@MainActivity)
        setContentView(customView)
    }
}
