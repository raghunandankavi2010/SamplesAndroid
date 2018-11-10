package com.example.raghu.constraintlayout_demo

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.view.View
import kotlinx.android.synthetic.main.constraint_set_layout1.*


/**
 * Created by raghu on 19/3/18.
 */

class ConstraintSetActivity : AppCompatActivity() {

    private var mConstraintSet1 = ConstraintSet() // create a Constraint Set
    private var mConstraintSet2 = ConstraintSet()
    private lateinit var mRootLayout:ConstraintLayout
    private var original = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_set_layout1)
        mRootLayout = findViewById<View>(R.id.activity_constraintset_example) as ConstraintLayout
        // Note that this can also be achieved by calling
        // `mConstraintSetNormal.load(this, R.layout.constraintset_example_main);`
        // Since we already have an inflated ConstraintLayout in `mRootLayout`, clone() is
        // faster and considered the best practice.
        mConstraintSet1.clone(mRootLayout)
        mConstraintSet2.load(this, R.layout.constraint_set_layout2)

        click.setOnClickListener({
            TransitionManager.beginDelayedTransition(mRootLayout)
            if (original)
                mConstraintSet2.applyTo(mRootLayout)
            else
                mConstraintSet1.applyTo(mRootLayout)
            original = !original

        })
    }
}
