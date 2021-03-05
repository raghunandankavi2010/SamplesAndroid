package com.example.circleripple

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.min

class CircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()

    companion object {
        private const val defaultStrokeWidth = 2
        private var rippleStrokeWidth = 0
        const val circle_radius1 = 0.5F
        const val circle_radius2 = 0.675F
    }

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CircleView)
        rippleStrokeWidth = attr.getDimensionPixelSize(R.styleable.CircleView_strokeWidth, defaultStrokeWidth)
        paint.apply {
            isAntiAlias = true
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = rippleStrokeWidth.toFloat()
        }
        attr.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        val radius: Float = min(width, height) / 2F
        canvas?.drawCircle(radius, radius, (radius * circle_radius1) - rippleStrokeWidth, paint)
        canvas?.drawCircle(radius, radius, (radius * circle_radius2) - rippleStrokeWidth, paint)
    }

    fun setCircleColor(nColor: String?) {
        nColor?.let {
            paint.apply { color = Color.parseColor(nColor) }
            invalidate()
        }
    }
}
