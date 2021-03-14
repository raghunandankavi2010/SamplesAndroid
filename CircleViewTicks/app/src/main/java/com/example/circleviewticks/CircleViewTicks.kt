package com.example.circleviewticks


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.circularprogressbar.utils.px
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin


class CircleViewTicks @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_RADIUS = 100
        private const val DEFAULT_STROKE_WIDTH = 20f
        private const val TICK_COUNT = 10
    }

    // Used to draw pretty much anything on a canvas, which is what we will be drawing on
    private val paint = Paint().apply {
        // how we want the arcs to be draw, we want to make sure the arc centers are not colored
        // so we use a STROKE instead.
        strokeWidth = DEFAULT_STROKE_WIDTH
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.BLACK
    }

    private val tPaint = Paint().apply {
        // how we want the arcs to be draw, we want to make sure the arc centers are not colored
        // so we use a STROKE instead.
        strokeWidth = 10f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.GREEN
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // how we want the arcs to be draw, we want to make sure the arc centers are not colored
        // so we use a STROKE instead.
        strokeWidth = 2f
        textSize = 25f
        textAlign = Paint.Align.CENTER
        style = Paint.Style.STROKE
        color = Color.RED

    }

    private val radius = DEFAULT_RADIUS


    init {

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

        setMeasuredDimension(width, height)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius.toFloat(), paint)
            drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius + 40 + DEFAULT_STROKE_WIDTH, tPaint)

        }

        for (i in 0 until TICK_COUNT) {
            val startAngle =
                    Math.toRadians(0.0).toFloat()
            val angle = ((startAngle + i) * (Math.toRadians(360.0) / TICK_COUNT)).toFloat()
            // above can also be
            // val divisionAngle = Math.toRadians(360.0) / TICK_COUNT ie 360/10 = 36
            // val angle = divisionAngle * i 36 *i ie 36 * 0 and 36 *1 and 36 *2 and so on..

            // get points on circumference of circle
            // center + r cos angle
            // center + r sin angle
            val x = ((radius + DEFAULT_STROKE_WIDTH) * cos(angle.toDouble()) + width / 2)
            val y = ((radius + DEFAULT_STROKE_WIDTH) * sin(angle.toDouble()) + height / 2)
            val x1 = ((radius + 20 + DEFAULT_STROKE_WIDTH) * cos(angle.toDouble()) + width / 2)
            val y1 = ((radius + 20 + DEFAULT_STROKE_WIDTH) * sin(angle.toDouble()) + height / 2)

            val x2 = ((radius + 40 + DEFAULT_STROKE_WIDTH) * cos(angle.toDouble()) + width / 2)
            val y2 = ((radius + 40 + DEFAULT_STROKE_WIDTH) * sin(angle.toDouble()) + height / 2)

            val yPos = (y2 - (textPaint.descent() + textPaint.ascent()) / 2)

            canvas.apply {
                drawText(i.toString(), x2.toFloat(), yPos.toFloat(), textPaint)

                drawLine(
                        x.toFloat(),
                        y.toFloat(),
                        x1.toFloat(),
                        y1.toFloat(),
                        tPaint
                )
            }
        }
    }

}