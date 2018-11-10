package com.example.raghu.constraintlayout_demo

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.Guideline
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by raghu on 19/3/18.
 */

class GuideLineActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guideline_layout)

        val guideline = findViewById<View>(R.id.guideline5) as Guideline

        val end = (guideline.layoutParams as ConstraintLayout.LayoutParams).guideEnd
        val valueAnimator = ValueAnimator.ofInt(0, end)
        valueAnimator.duration = 500
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator ->
            val lp = guideline.layoutParams as ConstraintLayout.LayoutParams
            lp.guideEnd = valueAnimator.animatedValue as Int
            guideline.layoutParams = lp
        }
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.start()
    }
}
