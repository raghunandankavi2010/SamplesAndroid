package com.example.progressbarwithpercentage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnProgressBarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnp =  findViewById<NumberProgressBar> (R.id.numberbar1);
        bnp.setOnProgressBarListener(this)
        bnp.progress= 80
    }

    override fun onProgressChange(current: Int, max: Int) {

    }

}