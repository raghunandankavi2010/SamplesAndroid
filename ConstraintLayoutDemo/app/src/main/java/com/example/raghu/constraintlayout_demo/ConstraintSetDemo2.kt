package com.example.raghu.constraintlayout_demo

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import kotlinx.android.synthetic.main.activity_constraint_set_demo2.*

class ConstraintSetDemo2 : AppCompatActivity() {

    private var show =false
    private val constraintSet = ConstraintSet()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_set_demo2)
        backgroundImage.setOnClickListener {
            if(show)
                hideComponents() // if the animation is shown, we hide back the views
            else
                showComponents() // if the animation is NOT shown, we animate the views
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun showComponents(){
        show = true

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activtity_constraint_set_demo2_final)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hideComponents(){
        show = false


        constraintSet.clone(this, R.layout.activity_constraint_set_demo2)

        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }
}
