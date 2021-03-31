package com.example.cirlceimageview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import kotlin.math.min


class CircleImageView : AppCompatImageView {

    companion object {
        private val SCALE_TYPE = ScaleType.CENTER_CROP
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

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ) {
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

    protected open fun init() {
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
            mBitmapPaint.setShadowLayer(shadowRadius, 0f, 0f,
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
}
