package com.example.circleripple

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.animation.Animation

class MainActivity : AppCompatActivity() {

    private val defaultRippleScale = DEFAULT_RIPPLE_SCALE
    private val maxRippleDuration = MAX_RIPPLE_DURATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val circleView = findViewById<CircleView>(R.id.circleView)
        val animatorSet = generateRipple(circleView)
        animatorSet.start()
    }

    // play scale x animation alon with scale y animation plus alpha
    // this gives the effect of ripple animation.
    private fun generateRipple(target: View): AnimatorSet {
        val animatorList = ArrayList<Animator>()
        val rippleScale: Float = defaultRippleScale
        animatorList.add(provideAnimator(target = target, type = View.SCALE_X, scale = rippleScale))
        animatorList.add(provideAnimator(target = target, type = View.SCALE_Y, scale = rippleScale))
        animatorList.add(provideAnimator(target = target, type = View.ALPHA))
        return AnimatorSet().apply { playTogether(animatorList) }
    }

    private fun provideAnimator(
        target: View,
        type: Property<View, Float>,
        scale: Float = defaultRippleScale
    ): ObjectAnimator {
        val scaleAmount = if (type == View.ALPHA) 0f else scale
        return ObjectAnimator.ofFloat(target, type, scaleAmount).apply {
            repeatCount = Animation.INFINITE
            duration = maxRippleDuration
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                }

                override fun onAnimationStart(animation: Animator) {
                    // platform callback : ignore
                }

                override fun onAnimationRepeat(animation: Animator) {
                    // platform callback : ignore
                }

                override fun onAnimationCancel(animation: Animator) {
                    // platform callback : ignore
                }
            })
        }
    }

    companion object {
        const val MAX_RIPPLE_DURATION = 1250L
        const val DEFAULT_RIPPLE_SCALE = 1.7f
    }
}