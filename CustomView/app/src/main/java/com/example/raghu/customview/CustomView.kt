package com.example.raghu.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.*




class CustomView(context: Context) : View(context){


    private val paint: Paint = Paint()
    private var x1 = 200
    private var y1 = 200
    private var x2 = 800
    private var y2 = 220
    private var canImageMove =false
    private var prevX: Int = 0
    private var prevY: Int = 0
    private val rect =  Rect(x1, y1, x2, y2)
    private var mTouchSlop: Int = 0
    var mScreenHeight: Int = 0
    var mScreenWidth: Int = 0


    init{

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        val  configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mScreenWidth = w
        mScreenHeight = h
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(rect ,paint)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        val positionX = motionEvent.x.toInt()
        val positionY = motionEvent.y.toInt()
        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {

                if(positionX > rect.left && positionX < rect.right && positionY < rect.bottom && positionY > rect.top){
                    prevX = positionX
                    prevY = positionY
                    canImageMove = true
                }else{
                    Log.i("CustomView :","Touch not within rect")
                }

            }
            MotionEvent.ACTION_MOVE -> {

                if(canImageMove)
                {
                    // Check if we have moved far enough that it looks more like a
                    // scroll than a tap
                    val distY = Math.abs(positionY - prevY)
                    val distX = Math.abs(positionX - prevX)

                    if (distX > mTouchSlop || distY > mTouchSlop)
                    {
                       val deltaX =  positionX-prevX ;
                       val deltaY =  positionY-prevY;
                        // Check if delta is added, is the rectangle is within the visible screen
                        if((rect.left+ deltaX) > 0 && ((rect.right +deltaX) < mScreenWidth )
                                && (rect.top +deltaY) >0 && ((rect.bottom+deltaY)< mScreenHeight))
                        {
                            // invalidate current position as we are moving...
                            rect.left = rect.left + deltaX
                            rect.top = rect.top + deltaY
                            rect.right = rect.right + deltaX
                            rect.bottom = rect.bottom + deltaY

                            prevX = positionX
                            prevY = positionY

                            invalidate()
                        }
                    }
                }
            }

           MotionEvent.ACTION_UP -> {
               canImageMove = false
           }

        }

        return true
    }


/*    fun isPointOnLine(lineStaPt: PointF, lineEndPt: PointF, point: PointF): Boolean {
        val EPSILON = width
        if (Math.abs(lineStaPt.x - lineEndPt.x) < EPSILON) {
            // We've a vertical line, thus check only the x-value of the point.
            return Math.abs(point.x - lineStaPt.x) < EPSILON
        } else {
            val m = (lineEndPt.y - lineStaPt.y) / (lineEndPt.x - lineStaPt.x)
            val b = lineStaPt.y - m * lineStaPt.x
            return Math.abs(point.y - (m * point.x + b)) < EPSILON
        }
    }*/
}