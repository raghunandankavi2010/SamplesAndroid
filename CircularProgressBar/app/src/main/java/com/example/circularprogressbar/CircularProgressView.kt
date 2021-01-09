package com.example.circularprogressbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.circularprogressbar.utils.px
import kotlin.math.max
import kotlin.math.min

class CircularProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_STROKE_WIDTH = 20f
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
    }

    // required to draw the arcs
    private val rectF = RectF()

    private val r: Rect = Rect()

    // Used to draw pretty much anything on a canvas, which is what we will be drawing on
    private val paint = Paint().apply {
        // how we want the arcs to be draw, we want to make sure the arc centers are not colored
        // so we use a STROKE instead.
        strokeWidth = DEFAULT_STROKE_WIDTH
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }


    private val textPaint = TextPaint().apply {
        isAntiAlias = true
    }

    // default percentage set to 0
    private var percentage = 0

    private var _textColor = DEFAULT_TEXT_COLOR
    var textColor
        get() = _textColor
        set(value) {
            _textColor = value
            textPaint.color = _textColor
            invalidate()
        }

    init {
        setupAttributes(attrs)

    }


    private fun setupAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView)
            textColor = a.getColor(
                R.styleable.CircularProgressView_percentage_color,
                DEFAULT_TEXT_COLOR
            )
            a.recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = paddingLeft + paddingRight
        var height = paddingTop + paddingBottom

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize
        } else {
            width += 200.px
            width = max(width, suggestedMinimumWidth)
            if (widthMode == MeasureSpec.AT_MOST) width = min(widthSize, width)
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize
        } else {
            height += 200.px
            height = max(height, suggestedMinimumHeight)
            if (heightMode == MeasureSpec.AT_MOST) height = min(height, heightSize)
        }

        val strokeRatio = (width / 8).toFloat()

        val rectWidthStart = 0f + strokeRatio
        val rectWidthEnd = width - strokeRatio

        val rectHeightStart = 0f + strokeRatio
        val rectHeightEnd = height - strokeRatio

        rectF.set(rectWidthStart, rectHeightStart, rectWidthEnd, rectHeightEnd)
        textPaint.textSize = rectHeightEnd * 0.2f

        setMeasuredDimension(width, height)
    }


    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            // draw an arc that will represent an empty loading view
            it.drawArc(rectF, 0f, 360f, false, paint.apply {
                color = ContextCompat.getColor(context, R.color.grey)

            })

            // get the actual percentage as a float
            val fillPercentage = (360 * (percentage / 100.0)).toFloat()

            // draw the arc that will represent the percentage filled up
            it.drawArc(rectF, 270f, fillPercentage, false, paint.apply {
                color = ContextCompat.getColor(context, R.color.green) // filled percentage color

            })
            drawPercentage(it, textPaint, percentage.toString().plus("%"))

        }
    }


    private fun drawPercentage(canvas: Canvas, paint: Paint, text: String) {

        canvas.getClipBounds(r)
        val cHeight: Int = r.height()
        val cWidth: Int = r.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x: Float = cWidth / 2f - r.width() / 2f - r.left
        val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }

    fun setPercentage(percentage: Int) {
        this.percentage = percentage
        invalidate()
    }
}