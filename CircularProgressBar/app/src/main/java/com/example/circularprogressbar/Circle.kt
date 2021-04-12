package com.example.circularprogressbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.example.circularprogressbar.utils.px
import kotlin.math.max
import kotlin.math.min

class Circle @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_TEXT_COLOR = Color.BLACK
        private const val DEFAULT_CIRCLE_COLOR = Color.GREEN
        private const val DEFAULT_TEXT = "0"
    }

    // required to draw the arcs
    private val rectF = RectF()

    private val r: Rect = Rect()

    // Used to draw pretty much anything on a canvas, which is what we will be drawing on
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }


    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
    }


    private var _circleColor = DEFAULT_CIRCLE_COLOR
    var circleColor
        get() = _circleColor
        set(value) {
            _circleColor = value
            paint.color = _circleColor
            invalidate()
        }

    private var radius: Int = 0

    private var _text: String = DEFAULT_TEXT
    var text
        get() = _text
        set(value) {
            _text = value
            invalidate()
        }


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

        radius = Math.min(width/2,height/2)
        textPaint.textSize = height/2 * 0.5f

        setMeasuredDimension(width, height)
    }


    @SuppressLint("CanvasSize")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {

            it.drawCircle(canvas.width/2.toFloat(),canvas.height/2.toFloat(),radius.toFloat(),paint)
            drawText(canvas,textPaint,_text)
        }
    }


    private fun drawText(canvas: Canvas, paint: Paint, text: String) {

        canvas.getClipBounds(r)
        val cHeight: Int = r.height()
        val cWidth: Int = r.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x: Float = cWidth / 2f - r.width() / 2f - r.left
        val y: Float = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }

}