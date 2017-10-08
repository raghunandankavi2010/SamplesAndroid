package com.example.raghu.customshape

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by raghu on 8/10/17.
 */


class ShapeImageView : ImageView {

    private val drawRect = RectF()
    private val bitmapRect = RectF()

    private val shaderMatrix = Matrix()

    private lateinit var bitmapShader: BitmapShader
    private lateinit var drawPaint: Paint
    private lateinit var circlePaint: Paint
    private lateinit var bitmap: Bitmap

    private lateinit var path: Path

    private var radius: Float = 0f
    private var center_x: Float = 0f
    private var center_y: Float = 0f

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun init(context: Context, attrs: AttributeSet?) {

        if (!isInEditMode) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
            bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            bitmapRect.set(0F, 0F, bitmap.width.toFloat(), bitmap.height.toFloat())
        }

        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        circlePaint.style = Paint.Style.STROKE
        circlePaint.color = Color.RED
        drawPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        drawPaint.style = Paint.Style.FILL
        drawPaint.shader = bitmapShader
    }

    fun refresh() {
        this.path = path(drawRect)
        invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        if (w > 0 && h > 0) {
            drawRect.set(0F, 0F, w.toFloat(), h.toFloat())
            drawRect.left += paddingStart
            drawRect.right -= paddingEnd
            drawRect.top += paddingTop
            drawRect.bottom -= paddingBottom

            shaderMatrix.setRectToRect(bitmapRect, drawRect, Matrix.ScaleToFit.CENTER)
            bitmapShader.setLocalMatrix(shaderMatrix)

            this.path = path(drawRect)
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawRect.width() > 0F && drawRect.height() > 0F) {
            canvas!!.drawPath(path, drawPaint)
            canvas.drawCircle(center_x,center_y,radius,circlePaint)
        }
    }

    fun path(bounds: RectF): Path {
        val path = Path()

        radius = when {
            bounds.width() > bounds.height() -> bounds.height() / 2F
            else -> bounds.width() / 2F
        }

        center_x = bounds.centerX()
        center_y = bounds.centerY()
        path.addCircle(center_x, center_y, radius, Path.Direction.CW)

        return path
    }

}