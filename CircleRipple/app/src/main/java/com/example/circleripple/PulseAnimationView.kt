package com.example.circleripple

import kotlin.jvm.JvmOverloads
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.example.circleripple.PulseAnimationView
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation

class PulseAnimationView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var mRadius = 200f
    private val mPaint = Paint()

    init {
        mPaint.apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }
    }


    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        val growAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", 0f, 200f
        )
        growAnimator.duration = ANIMATION_DURATION.toLong()
        growAnimator.interpolator = LinearInterpolator()
        growAnimator.repeatCount = Animation.INFINITE
        growAnimator.start()

    }


    /**
     * Required setter for the animated property.
     * Called by the Animator to update the property.
     *
     * @param radius This view's radius property.
     */
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
        canvas.drawCircle(width/2.toFloat(), height/2.toFloat(), mRadius, mPaint)
    }

    companion object {
        private const val ANIMATION_DURATION = 4000
        private const val ANIMATION_DELAY: Long = 1000
        private const val COLOR_ADJUSTER = 5
    }
}