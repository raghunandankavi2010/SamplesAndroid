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

class PulseAnimationView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var mX = 0f
    private var mY = 0f
    private var mRadius = 0f
    private val mPaint = Paint()
    private val mPulseAnimatorSet: AnimatorSet? = AnimatorSet()
    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // This method is called when the size of the view changes.
        // For this app, it is only called when the activity is started or restarted.
        // getWidth() cannot return anything valid in onCreate(), but it does here.
        // We create the animators and animator set here once, and handle the starting and
        // canceling in the event handler.

        // Animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates an increasingly larger circle from a
        // radius of 0 to the width of the view.
        val growAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", 0f, width.toFloat()
        )
        growAnimator.duration = ANIMATION_DURATION.toLong()
        growAnimator.interpolator = LinearInterpolator()

        // Create a second animator to
        // animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates a shrinking circle
        // from a radius of the view's width to 0.
        // Add a delay to starting the animation.
        val shrinkAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", width.toFloat(), 0f
        )
        shrinkAnimator.duration = ANIMATION_DURATION.toLong()
        shrinkAnimator.interpolator = LinearOutSlowInInterpolator()
        shrinkAnimator.startDelay = ANIMATION_DELAY

        // If you don't need a delay between the two animations,
        // you can use one animator that repeats in reverse.
        // Uses the default AccelerateDecelerateInterpolator.
        val repeatAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", 0f, width.toFloat()
        )
        repeatAnimator.startDelay = ANIMATION_DELAY
        repeatAnimator.duration = ANIMATION_DURATION.toLong()
        repeatAnimator.repeatCount = 1
        repeatAnimator.repeatMode = ValueAnimator.REVERSE

        // Create an AnimatorSet to combine the two animations into a sequence.
        // Play the expanding circle, wait, then play the shrinking circle.
        mPulseAnimatorSet!!.play(growAnimator).before(shrinkAnimator)
        mPulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {

            // Where the center of the circle will be.
            mX = event.x
            mY = event.y

            // If there is an animation running, cancel it.
            // This resets the AnimatorSet and its animations to the starting values.
            if (mPulseAnimatorSet != null && mPulseAnimatorSet.isRunning) {
                mPulseAnimatorSet.cancel()
            }
            // Start the animation sequence.
            mPulseAnimatorSet!!.start()
        }
        return super.onTouchEvent(event)
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
        canvas.drawCircle(mX, mY, mRadius, mPaint)
    }

    companion object {
        private const val ANIMATION_DURATION = 4000
        private const val ANIMATION_DELAY: Long = 1000
        private const val COLOR_ADJUSTER = 5
    }
}