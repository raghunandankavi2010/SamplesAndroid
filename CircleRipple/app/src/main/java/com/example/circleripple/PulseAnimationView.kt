package com.example.circleripple

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator


class PulseAnimationView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var mRadius = 200f
    private val mPaint = Paint()
    private val mPulseAnimatorSet = AnimatorSet()

    init {
        mPaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
    }


    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        // grow radius animation
        val growAnimator = ObjectAnimator.ofFloat(
                this,
                "radius", 0f, 200f
        )
        growAnimator.duration = ANIMATION_DURATION.toLong()
        growAnimator.interpolator = LinearInterpolator()

        // shrink radius animation
        val shrinkAnimator = ObjectAnimator.ofFloat(this,
                "radius", 200f, 0f)
        shrinkAnimator.duration = ANIMATION_DURATION.toLong()
        shrinkAnimator.interpolator = LinearOutSlowInInterpolator()
        shrinkAnimator.startDelay = ANIMATION_DELAY

        // animation in reverse and repeat
        val repeatAnimator = ObjectAnimator.ofFloat(this,
                "radius", 0f, 200f)
        repeatAnimator.startDelay = ANIMATION_DELAY
        repeatAnimator.duration = ANIMATION_DURATION.toLong()
        repeatAnimator.repeatCount = Animation.INFINITE
        repeatAnimator.repeatMode = ValueAnimator.REVERSE

        // play grow animation first and then shrink animation
        // repeat grow after shrink
        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator)
        mPulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator)
        mPulseAnimatorSet.start()
    }


    fun setRadius(radius: Float) {
        mRadius = radius
        // Calculate a new color from the radius.
        mPaint.color =
            Color.GREEN + radius.toInt() / COLOR_ADJUSTER
        // Updating the property does not automatically redraw.
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), mRadius, mPaint)
    }

    companion object {
        private const val ANIMATION_DURATION = 4000
        private const val ANIMATION_DELAY: Long = 1000
        private const val COLOR_ADJUSTER = 5
    }
}