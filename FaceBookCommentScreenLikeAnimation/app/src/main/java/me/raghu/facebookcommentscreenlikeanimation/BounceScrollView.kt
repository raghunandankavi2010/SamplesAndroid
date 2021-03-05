package me.raghu.facebookcommentscreenlikeanimation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.content.res.Resources
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView


class BounceScrollView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : NestedScrollView(context, attrs, defStyleAttr) {
    private var isScrollHorizontally: Boolean
    private var mDamping: Float
    var isIncrementalDamping: Boolean
    private var mBounceDelay: Long
    //private var mTriggerOverScrollThreshold: Int
    var isDisableBounce: Boolean
    private var mInterpolator: Interpolator
    private var mChildView: View? = null
    private var mStart = 0f
    private var mPreDelta = 0
    private var mOverScrolledDistance = 0
    private var mAnimator: ObjectAnimator? = null
    private var mScrollListener: OnScrollListener? = null
    private var mOverScrollListener: OnOverScrollListener? = null
    private var mDismissListener: OnDismissListener? = null
    private var startX = 0F
    private var startY = 0F
    private var dX = 0F
    private var dY = 0F
    private val deviceHeight = Resources.getSystem().displayMetrics.heightPixels
    private var percentVertical = 0F

    private var scrollable: Boolean = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun canScrollVertically(direction: Int): Boolean {
        return !isScrollHorizontally
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return isScrollHorizontally
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (mChildView == null && childCount > 0 || mChildView !== getChildAt(0)) {
            mChildView = getChildAt(0)

        }

   return if(scrollable){
            Log.i("Parent","Touch Intercepted")
            true
        }else {
            Log.i("Parent","Touch Intercepted false")
            super.onInterceptTouchEvent(ev)
        }
       // return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (mChildView == null || isDisableBounce || !scrollable) return super.onTouchEvent(ev)
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
               // Log.i("Parent","Touch Intercepted")
                mStart = if (isScrollHorizontally) ev.x else ev.y
                startX = ev.rawX
                startY = ev.rawY
                dX = x - startX
                dY = y - startY

            }
            MotionEvent.ACTION_MOVE -> {
                val delta: Float
                val dampingDelta: Int
                val now: Float = if (isScrollHorizontally) ev.x else ev.y
                delta = mStart - now
                dampingDelta = (delta / calculateDamping()).toInt()
                mStart = now
                var onePointerTouch = true
                if (mPreDelta <= 0 && dampingDelta > 0) {
                    onePointerTouch = false
                } else if (mPreDelta >= 0 && dampingDelta < 0) {
                    onePointerTouch = false
                }
                mPreDelta = dampingDelta
                if (onePointerTouch && canMove(dampingDelta)) {
                    mOverScrolledDistance += dampingDelta
                    if (isScrollHorizontally) {
                        mChildView!!.translationX = -mOverScrolledDistance.toFloat()
                    } else {
                        mChildView!!.translationY = -mOverScrolledDistance.toFloat()
                    }
                    if (mOverScrollListener != null) {
                        mOverScrollListener!!.onOverScrolling(mOverScrolledDistance <= 0, Math.abs(mOverScrolledDistance))
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mPreDelta = 0
                //mOverScrolledDistance = 0;
                val half = (Resources.getSystem().displayMetrics.heightPixels.toFloat()/2)
                val scrolledDistance = Math.abs(mOverScrolledDistance)
                percentVertical = (ev.rawY + dY) / deviceHeight.toFloat()
                Log.i("Scrolled Distance", "$scrolledDistance"+"half"+half)


                when (getDirection(startX = startX, startY = startY, endX = ev.rawX, endY = ev.rawY)) {
                    is Direction.UP -> {

                        mAnimator = if (scrolledDistance > half) {
                            //Log.i("Scrolled Distance up", "$mOverScrolledDistance nd half of device height $half")
                            ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, - Resources.getSystem().displayMetrics.heightPixels.toFloat())
                        } else {
                            ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, 0f)
                        }
                    }
                    is Direction.DOWN ->{
                        mAnimator = if (Math.abs(scrolledDistance) > half) {
                           // Log.i("Scrolled Distance down", "$mOverScrolledDistance and half of device height $half")
                            ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, Resources.getSystem().displayMetrics.heightPixels.toFloat())
                        } else {
                            ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, 0f)
                        }
                    }
                }

                cancelAnimator()
                if (isScrollHorizontally) {
                    mAnimator = ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_X, 0f)
                } else {
                    // mAnimator = ObjectAnimator.ofFloat(mChildView, View.TRANSLATION_Y, 0);
                }
                mAnimator?.setDuration(mBounceDelay)?.interpolator = mInterpolator
                if (mOverScrollListener != null) {
                    mAnimator?.addUpdateListener { animation ->
                        val value = animation.animatedValue as Float
                        mOverScrollListener!!.onOverScrolling(value <= 0, Math.abs(value.toInt()))
                    }
                }
                mAnimator?.addListener(object: Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                       mOverScrolledDistance = 0
                        startX = 0f
                        startY = 0f

                        val translationY = mChildView!!.translationY

                      //  Log.i("ScrollView height",""+mChildView!!.translationY )
                            if(translationY==Resources.getSystem().displayMetrics.heightPixels.toFloat() || translationY == -Resources.getSystem().displayMetrics.heightPixels.toFloat()){
                            mDismissListener?.onDismiss(true)
                        }
                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationStart(animation: Animator?) {

                    }

                })
                mAnimator?.start()
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun calculateDamping(): Float {
        var ratio: Float = if (isScrollHorizontally) {
            Math.abs(mChildView!!.translationX) * 1.0f / mChildView!!.measuredWidth
        } else {
            Math.abs(mChildView!!.translationY) * 1.0f / mChildView!!.measuredHeight
        }
        ratio += 0.2f
        return if (isIncrementalDamping) {
            mDamping / (1.0f - Math.pow(ratio.toDouble(), 2.0).toFloat())
        } else {
            mDamping
        }
    }

    private fun canMove(delta: Int): Boolean {
        return if (delta < 0) canMoveFromStart() else canMoveFromEnd()
    }

    private fun canMoveFromStart(): Boolean {
        return if (isScrollHorizontally) scrollX == 0 else scrollY == 0
    }

    private fun canMoveFromEnd(): Boolean {
        return if (isScrollHorizontally) {
            var offset = mChildView!!.measuredWidth - width
            offset = if (offset < 0) 0 else offset
            scrollX == offset
        } else {
            var offset = mChildView!!.measuredHeight - height
            offset = if (offset < 0) 0 else offset
            scrollY == offset
        }
    }

    private fun cancelAnimator() {
        if (mAnimator!=null && mAnimator!!.isRunning) {
            mAnimator?.cancel()
        }
    }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(scrollX, scrollY, oldl, oldt)
        if (mScrollListener != null) {
            mScrollListener!!.onScrolling(scrollX, scrollY)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimator()
    }

    var damping: Float
        get() = mDamping
        set(damping) {
            if (mDamping > 0) {
                mDamping = damping
            }
        }

    var bounceDelay: Long
        get() = mBounceDelay
        set(bounceDelay) {
            if (bounceDelay >= 0) {
                mBounceDelay = bounceDelay
            }
        }

    fun setBounceInterpolator(interpolator: Interpolator) {
        mInterpolator = interpolator
    }
 /*   var triggerOverScrollThreshold: Int
        get() = mTriggerOverScrollThreshold
        set(threshold) {
            if (threshold >= 0) {
                mTriggerOverScrollThreshold = threshold
            }
        }*/

    fun setOnScrollListener(scrollListener: OnScrollListener?) {
        mScrollListener = scrollListener
    }

    fun setOnOverScrollListener(overScrollListener: OnOverScrollListener?) {
        mOverScrollListener = overScrollListener
    }

    fun setOnDismissListener(onDismissListener: OnDismissListener?) {
        mDismissListener = onDismissListener
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    interface OnDismissListener {

        fun onDismiss(dimissable: Boolean)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    interface OnScrollListener {
        fun onScrolling(scrollX: Int, scrollY: Int)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    interface OnOverScrollListener {
        /**
         * @param fromStart LTR, the left is start; RTL, the right is start.
         */
        fun onOverScrolling(fromStart: Boolean, overScrolledDistance: Int)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private class DefaultQuartOutInterpolator : Interpolator {
        override fun getInterpolation(input: Float): Float {
            return (1.0f - Math.pow(1 - input.toDouble(), 4.0)).toFloat()
        }
    }

    companion object {
        private const val DEFAULT_DAMPING_COEFFICIENT = 4.0f
        private const val DEFAULT_SCROLlL_THRESHOLD = 600
        private const val DEFAULT_BOUNCE_DELAY: Long = 400
    }

    init {
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
        isFillViewport = true
        overScrollMode = View.OVER_SCROLL_NEVER
        val a = context.obtainStyledAttributes(attrs, R.styleable.BounceScrollView, 0, 0)
        mDamping = a.getFloat(R.styleable.BounceScrollView_damping, DEFAULT_DAMPING_COEFFICIENT)
        val orientation = a.getInt(R.styleable.BounceScrollView_scrollOrientation, 0)
        isScrollHorizontally = orientation == 1
        isIncrementalDamping = a.getBoolean(R.styleable.BounceScrollView_incrementalDamping, true)
        mBounceDelay = a.getInt(R.styleable.BounceScrollView_bounceDelay, DEFAULT_BOUNCE_DELAY.toInt()).toLong()
        //mTriggerOverScrollThreshold = a.getInt(R.styleable.BounceScrollView_triggerOverScrollThreshold, DEFAULT_SCROLL_THRESHOLD)
        isDisableBounce = a.getBoolean(R.styleable.BounceScrollView_disableBounce, false)
        val enable = a.getBoolean(R.styleable.BounceScrollView_nestedScrollingEnabled, true)
        a.recycle()
        isNestedScrollingEnabled = enable
        mInterpolator = DefaultQuartOutInterpolator()
    }

    private var left = Direction.LEFT()
    private var right = Direction.RIGHT()
    private var up = Direction.UP()
    private var down = Direction.DOWN()
    private var none = Direction.NONE()
    /**
     * return a Direction on which user is current scrolling by getting
     * start event coordinates when user press down and end event coordinates when user
     * moves the finger on view
     */
    private fun getDirection(startX: Float, startY: Float, endX: Float, endY: Float): Direction {
        val deltaX = endX - startX
        val deltaY = endY - startY

        return if (Math.abs(deltaX) > Math.abs(deltaY)) {
            //Scrolling Horizontal
            if (deltaX > 0) right else left
        } else {
            if (deltaY > 0) down else up
        }
    }

    fun setScrollable(isScrollable: Boolean) {
        scrollable = isScrollable

    }
}