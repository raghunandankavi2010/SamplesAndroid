package com.example.cirlceimageview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.SystemClock
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.cirlceimageview.listener.OnBadgeCountChangeListener
import com.example.cirlceimageview.util.DensityUtils
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class CircleImageView : AppCompatImageView {

    companion object {
        private val SCALE_TYPE = ScaleType.FIT_XY
        private const val DEFAULT_SHADOW_RADIUS = 5.0f
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_WIDTH = 2
        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_OVERLAY = false
        private const val SCALE_FACTOR = 0.5f
    }

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()
    private val mShaderMatrix = Matrix()
    private val mBitmapPaint = Paint()
    private val mBorderPaint = Paint()
    private val shadowPaint = Paint()
    private var mShowShadow: Boolean = false
    private var shadowRadius = DEFAULT_SHADOW_RADIUS
    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mBitmap: Bitmap? = null
    private var mBitmapShader: BitmapShader? = null
    private var mBitmapWidth: Int = 0
    private var mBitmapHeight: Int = 0
    private var mDrawableRadius: Float = 0.toFloat()
    private var mBorderRadius: Float = 0.toFloat()
    private var mColorFilter: ColorFilter? = null
    private var mReady: Boolean = false
    private var mSetupPending: Boolean = false
    private var mBorderOverlay: Boolean = false
    private val delayInMillis = 1000L
    private var lastClickMillis: Long = 0

    private var manager: DrawerManager? = null
    private var onBadgeCountChangeListener: OnBadgeCountChangeListener? = null

    private val r: Rect = Rect()

    private val circlePaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = Color.RED
    }


    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        color = Color.WHITE
        textSize = 30f
    }


    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ) {
        manager = DrawerManager(this@CircleImageView, attrs)
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)
        mBorderWidth =
            a.getDimensionPixelSize(
                R.styleable.CircleImageView_border_width,
                DEFAULT_BORDER_WIDTH
            )
        mBorderColor = a.getColor(
            R.styleable.CircleImageView_border_color,
            DEFAULT_BORDER_COLOR
        )
        mBorderOverlay =
            a.getBoolean(
                R.styleable.CircleImageView_border_overlay,
                DEFAULT_BORDER_OVERLAY
            )
        mShowShadow = a.getBoolean(R.styleable.CircleImageView_show_shadow, false)
        a.recycle()

        init()
    }

    fun init() {

        super.setScaleType(SCALE_TYPE)
        mReady = true

        if (mSetupPending) {
            setup()
            mSetupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) { String.format("ScaleType %s not supported.", scaleType) }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable == null) {
            return
        }

        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            mDrawableRadius,
            mBitmapPaint
        )

        if (mBorderWidth != 0) {
            canvas.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                mBorderRadius,
                mBorderPaint
            )
        }
        manager!!.drawBadge(canvas)

      /*  val angle = Math.toRadians(315.0)
        val x = width / 2 + cos(angle) * mDrawableRadius
        val y = height / 2 + sin(angle) * mDrawableRadius

        canvas.drawCircle(x.toFloat(), y.toFloat(), 30f, circlePaint)


        ContextCompat.getDrawable(context, R.drawable.bkg)
           ?.let { drawBackgroundForBadge(it, canvas, x.toFloat(), y.toFloat()) }

        drawText(canvas, x.toFloat(), y.toFloat(), textPaint, "100")*/
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        mBitmap = bm
        setup()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        mBitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === mColorFilter) {
            return
        }
        mColorFilter = cf
        mBitmapPaint.colorFilter = mColorFilter
        invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        return when (drawable) {
            null -> {
                null
            }
            is BitmapDrawable -> drawable.bitmap
            else -> try {
                val width =
                    if (drawable is ColorDrawable) COLOR_DRAWABLE_WIDTH else drawable.intrinsicWidth
                val height =
                    if (drawable is ColorDrawable) COLOR_DRAWABLE_WIDTH else drawable.intrinsicHeight
                val bitmap: Bitmap = Bitmap.createBitmap(width, height, BITMAP_CONFIG)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            } catch (e: OutOfMemoryError) {
                null
            }
        }
    }

    private fun setup() {
        if (!mReady) {
            mSetupPending = true
            return
        }
        if (mBitmap == null) {
            return
        }
        mBitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.shader = mBitmapShader

        mBorderPaint.style = Paint.Style.STROKE
        mBorderPaint.isAntiAlias = true
        mBorderPaint.color = mBorderColor
        mBorderPaint.strokeWidth = mBorderWidth.toFloat()

        shadowPaint.style = Paint.Style.STROKE
        shadowPaint.isAntiAlias = true
        shadowPaint.color = mBorderColor
        shadowPaint.strokeWidth = mBorderWidth.toFloat()

        mBitmapHeight = mBitmap!!.height
        mBitmapWidth = mBitmap!!.width

        mBorderRect.set(0f, 0f, width.toFloat(), height.toFloat())
        mBorderRadius = min(
            (mBorderRect.height() - mBorderWidth) / 2,
            (mBorderRect.width() - mBorderWidth) / 2
        )

        mDrawableRect.set(mBorderRect)
        if (!mBorderOverlay) {
            mDrawableRect.inset(mBorderWidth.toFloat(), mBorderWidth.toFloat())
        }
        mDrawableRadius = min(mDrawableRect.height() / 2, mDrawableRect.width() / 2)

        if (mShowShadow) {
            mBorderRadius -= shadowRadius
            mDrawableRadius -= shadowRadius
            (parent as ViewGroup).setLayerType(View.LAYER_TYPE_SOFTWARE, mBitmapPaint)
            mBitmapPaint.setShadowLayer(
                shadowRadius, 0f, 0f,
                ContextCompat.getColor(context, R.color.black)
            )
        }
        updateShaderMatrix()
        invalidate()
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f

        mShaderMatrix.set(null)

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / mBitmapHeight.toFloat()
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * SCALE_FACTOR
        } else {
            scale = mDrawableRect.width() / mBitmapWidth.toFloat()
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * SCALE_FACTOR
        }

        mShaderMatrix.setScale(scale, scale)
        mShaderMatrix.postTranslate(
            (dx + SCALE_FACTOR).toInt() + mDrawableRect.left,
            (dy + SCALE_FACTOR).toInt() + mDrawableRect.top
        )

        mBitmapShader!!.setLocalMatrix(mShaderMatrix)
    }

    override fun performClick(): Boolean {
        var consumedFreshClick = false
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickMillis > delayInMillis) {
            consumedFreshClick = super.performClick()
        }
        lastClickMillis = now
        return consumedFreshClick
    }

    private fun drawText(canvas: Canvas, x: Float, y: Float, paint: Paint, text: String) {

        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x1: Float = x - r.width() / 2f - r.left
        val y1: Float = y + r.height() / 2f - r.bottom
        canvas.drawText(text, x1, y1, paint)
    }

    fun drawBackgroundForBadge(drawable: Drawable, canvas: Canvas, x: Float, y: Float) {
        drawable.setBounds(
            0,
            0,
            70,
            70
        )
        canvas.save()
        canvas.translate(
            x - drawable.bounds.width()/2,
            y - drawable.bounds.height()/2
        )
        drawable.draw(canvas)
        canvas.restore()
    }
    
      /**
     * Register a callback to be invoked when counter of a badge changed.
     * @param listener the callback that will run.
     */
    fun setOnBadgeCountChangeListener(listener: OnBadgeCountChangeListener?) {
        onBadgeCountChangeListener = listener
    }

    /**
     * @return the badge counter value
     */
    val badgeValue: Float
        get() = manager!!.badge.value

    /**
     * Set a badge counter value and add change listener if it registered
     * @param badgeValue new counter value
     */
    fun setBadgeValue(badgeValue: Float): CircleImageView {
        manager!!.badge.value = badgeValue
        if (onBadgeCountChangeListener != null) {
            onBadgeCountChangeListener!!.onCountChange(badgeValue)
        }
        invalidate()
        return this
    }

    /**
     * Get the maximum value of a badge (by default setting 99)
     */
    val maxBadgeValue: Int
        get() = manager!!.badge.maxValue

    /**
     * Set the maximum value of a badge (by default setting 99)
     * @param maxBadgeValue new maximum value for counter
     */
    fun setMaxBadgeValue(maxBadgeValue: Int): CircleImageView {
        manager!!.badge.maxValue = maxBadgeValue
        invalidate()
        return this
    }

    /**
     * @return state of a badge visible
     */
    val isVisibleBadge: Boolean
        get() = manager!!.badge.isVisible

    /**
     * Set a badge visible state
     * @param  visible If state true a badge will be visible
     */
    fun visibleBadge(visible: Boolean): CircleImageView {
        manager!!.badge.isVisible = visible
        invalidate()
        return this
    }

    /**
     * @return state of a badge vertical stretched
     */
    val isRoundBadge: Boolean
        get() = manager!!.badge.isRoundBadge

    /**
     * Define whether a badge can be stretched vertically
     * @param roundBadge If param is true, a badge can be stretched vertically
     */
    fun setRoundBadge(roundBadge: Boolean): CircleImageView {
        manager!!.badge.isRoundBadge = roundBadge
        invalidate()
        return this
    }

    /**
     * @return state of a badge fixed radius by width
     */
    val isFixedRadius: Boolean
        get() = manager!!.badge.isFixedRadius

    /**
     * Define whether a badge can be with fixed radius by width.
     * Badge can have only circle or square form.
     * @param fixedRadius If param is true, a badge radius fixed
     */
    fun setFixedRadius(fixedRadius: Boolean): CircleImageView {
        manager!!.badge.isFixedRadius = fixedRadius
        invalidate()
        return this
    }

    /**
     * State of a badge whether a badge can be oval form after first value number of counter
     * @return state of a badge
     */
    val isBadgeOvalAfterFirst: Boolean
        get() = manager!!.badge.isOvalAfterFirst

    /**
     * Define whether a badge can be oval form after first value number of counter
     * Please use this method only for custom drawable badge background. See [.setBadgeBackground].
     * @param badgeOvalAfterFirst If param is true, a badge can be oval form after first value
     */
    fun setBadgeOvalAfterFirst(badgeOvalAfterFirst: Boolean): CircleImageView {
        manager!!.badge.isOvalAfterFirst = badgeOvalAfterFirst
        invalidate()
        return this
    }

    /**
     * @return State whether the counter is showing
     */
    val isShowCounter: Boolean
        get() = manager!!.badge.isShowCounter

    /**
     * Specify whether the counter can be showing on a badge
     * @param showCounter Specify whether the counter is shown
     */
    fun setShowCounter(showCounter: Boolean): CircleImageView {
        manager!!.badge.isShowCounter = showCounter
        invalidate()
        return this
    }

    /**
     * State of a badge has limit counter
     * @return state of a badge has limit
     */
    val isLimitValue: Boolean
        get() = manager!!.badge.isLimitValue

    /**
     * Define whether a badge counter can have limit
     * @param badgeValueLimit If param is true, after max value (default 99) a badge will have counter 99+
     * Otherwise a badge will show the current value, e.g 101
     */
    fun setLimitBadgeValue(badgeValueLimit: Boolean): CircleImageView {
        manager!!.badge.isLimitValue = badgeValueLimit
        invalidate()
        return this
    }

    /**
     * Get the current badge background color
     */
    val badgeColor: Int
        get() = manager!!.badge.badgeColor

    /**
     * Set the background color for a badge
     * @param badgeColor the color of the background
     */
    fun setBadgeColor(badgeColor: Int): CircleImageView {
        manager!!.badge.badgeColor = badgeColor
        invalidate()
        return this
    }

    /**
     * Get the current text color of a badge
     */
    val badgeTextColor: Int
        get() = manager!!.badge.badgeTextColor

    /**
     * Set the text color for a badge
     * @param badgeTextColor the color of a badge text
     */
    fun setBadgeTextColor(badgeTextColor: Int): CircleImageView {
        manager!!.badge.badgeTextColor = badgeTextColor
        invalidate()
        return this
    }

    /**
     * Get the current text size of a badge
     */
    val badgeTextSize: Float
        get() = manager!!.badge.badgeTextSize

    /**
     * Set the text size for a badge
     * @param badgeTextSize the size of a badge text
     */
    fun setBadgeTextSize(badgeTextSize: Float): CircleImageView {
        manager!!.badge.badgeTextSize = DensityUtils.dpToPx(badgeTextSize)
        invalidate()
        return this
    }

    /**
     * Get padding of a badge
     */
    val badgePadding: Float
        get() = manager!!.badge.padding

    /**
     * Set a badge padding
     * @param badgePadding the badge padding
     */
    fun setBadgePadding(badgePadding: Int): CircleImageView {
        manager!!.badge.padding = DensityUtils.txtPxToSp(badgePadding.toFloat())
        invalidate()
        return this
    }

    /**
     * Get a badge radius
     */
    val badgeRadius: Float
        get() = manager!!.badge.radius

    /**
     * Set the badge fixed radius. Radius will not respond to changes padding or width of text.
     * @param fixedBadgeRadius badge fixed radius value
     */
    fun setFixedBadgeRadius(fixedBadgeRadius: Float): CircleImageView {
        manager!!.badge.fixedRadiusSize = fixedBadgeRadius
        invalidate()
        return this
    }

    /**
     * Get the current typeface [Typeface] of a badge
     */
    val badgeTextFont: Typeface
        get() = manager!!.badge.badgeTextFont

    /**
     * Set the typeface for a badge text
     * @param font Font for a badge text
     */
    fun setBadgeTextFont(font: Typeface?): CircleImageView {
        manager!!.badge.badgeTextFont = font
        invalidate()
        return this
    }

    /**
     * Get the style of the badge text. Matches the [Typeface] text style
     */
    val badgeTextStyle: Int
        get() = manager!!.badge.textStyle

    /**
     * Set the style of the badge text. Matches the [Typeface] text style
     * @param badgeTextStyle Can be normal, bold, italic, bold_italic
     */
    fun setBadgeTextStyle(badgeTextStyle: Int): CircleImageView {
        manager!!.badge.textStyle = badgeTextStyle
        invalidate()
        return this
    }

    /**
     * Get the background of a badge.
     */
    val badgeBackground: Int
        get() = manager!!.badge.badgeBackground

    /**
     * Get the background of a badge [Drawable] or null.
     */
    val badgeBackgroundDrawable: Drawable?
        get() = manager!!.badge.backgroundDrawable

    /**
     * Set the custom background of a badge
     * @param badgeBackground the [Drawable] background of a badge from resources
     */
    fun setBadgeBackground(badgeBackground: Drawable?): CircleImageView {
        manager!!.badge.backgroundDrawable = badgeBackground
        invalidate()
        return this
    }

    /**
     * Clear counter badge value
     */
    fun clearBadge() {
        manager!!.badge.clearValue()
        invalidate()
    }

    /**
     * Get position of a badge [BadgePosition].
     * @return [BadgePosition] position on ImageView by index
     */
    val badgePosition: Int
        get() = manager!!.badge.position

    /**
     * Set badge position [BadgePosition] on ImageView
     * @param position on this view [BadgePosition] top_left, top_right, bottom_left, bottom_right
     */
    fun setBadgePosition(position: Int): CircleImageView {
        manager!!.badge.position = position
        invalidate()
        return this
    }
    
}


