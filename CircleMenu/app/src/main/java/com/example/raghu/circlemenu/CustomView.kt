package com.example.raghu.circlemenu

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.provider.MediaStore.Images.Media.getBitmap

import android.view.ScaleGestureDetector
import android.widget.Toast
import android.util.Property
import android.R.attr.start
import android.view.animation.LinearInterpolator
import android.graphics.PorterDuff
import android.opengl.ETC1.getWidth
import android.view.animation.Transformation
import android.view.animation.Animation
import java.lang.reflect.Array.getLength
import android.graphics.PathMeasure
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat.setTranslationX
import android.support.v4.view.ViewCompat.setTranslationX
import android.support.v4.view.ViewCompat.setTranslationY
import android.support.v4.view.ViewCompat.setTranslationX
import java.lang.reflect.Array.getLength
import android.R.attr.animation
import android.animation.*
import android.view.animation.AccelerateDecelerateInterpolator
import java.lang.reflect.Array.getLength
import android.R.attr.path
import android.R.attr.start
import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.animation.Animator
import android.animation.Animator.AnimatorListener




/**
 * Created by raghu on 23/8/17.
 */
class CustomView : View {

    var radius: Float = 0f
    var x1: Float = 0f
    var y1: Float = 0f
    var x2: Float = 0f
    var y2: Float = 0f
    var x3: Float = 0f
    var y3: Float = 0f
    var x4: Float = 0f
    var y4: Float = 0f
    var _x: Float = 0f
    var _y: Float = 0f

    var mat: Matrix? = null
    var bitmap1: Bitmap? = null
    var bitmap2: Bitmap? = null
    var bitmap3: Bitmap? = null
    var bitmap4: Bitmap? = null
    var paint: Paint? = null
    var paint2: Paint? = null

    var check: Boolean = false


    var angle1: Double = 315.0
    var angle2: Double = 270.0
    var angle3: Double = 225.0
    var angle4: Double = 90.0

    var sweepAngle1 : Float = 135f
    var sweepAngle2 : Float = 180f
    var sweepAngle3 : Float = 225f
    var sweepAngle4 : Float = 90f

    var center_x : Float = 0f
    var center_y : Float = 0f

    var pos_b1:Int = 1
    var pos_b2:Int =2
    var pos_b3:Int = 3
    var pos_b4:Int = 4

    var oval: RectF = RectF()




    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {

        mat = Matrix()
        paint = Paint()
        paint?.color = Color.RED
        paint?.style = Paint.Style.STROKE
        paint?.strokeWidth = 5f
        paint?.isAntiAlias = true

        paint2 = Paint()
        paint2?.color = Color.GREEN
        paint2?.style = Paint.Style.STROKE
        paint2?.strokeWidth = 5f
        paint2?.isAntiAlias = true

        bitmap1 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)
        bitmap2 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)
        bitmap3 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)
        bitmap4 = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher)
        radius = 300f


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {


        val width: Float = width.toFloat()
        val height: Float = height.toFloat()
        center_x = width / 2
        center_y = height / 2
        _x = center_x- bitmap1?.width!!.div(2)
        _y = center_y-bitmap1?.height!!.div(2)
        canvas.drawBitmap(bitmap1,_x,_y,paint)


        if(!check) {


            x1 = (center_x + (radius * Math.cos(Math.toRadians(angle1)))).toFloat()
            y1 = (center_y + (radius * Math.sin(Math.toRadians(angle1)))).toFloat()


            x2 = (center_x + (radius * Math.cos(Math.toRadians(angle2)))).toFloat()
            y2 = (center_y + (radius * Math.sin(Math.toRadians(angle2)))).toFloat()


            x3 = (center_x + (radius * Math.cos(Math.toRadians(angle3)))).toFloat()
            y3 = (center_y + (radius * Math.sin(Math.toRadians(angle3)))).toFloat()


            x4 = (center_x + (radius * Math.cos(Math.toRadians(angle4)))).toFloat()
            y4 = (center_y + (radius * Math.sin(Math.toRadians(angle4)))).toFloat()

        }

        /**
         * Draw 1st Image
         */


        x1 = x1 - bitmap1?.width!!.div(2)
         y1 = y1 - bitmap1?.height!!.div(2)

        canvas.drawBitmap(bitmap1, x1,y1,paint)

        /**
         * Draw 2nd Image
         */

        x2 = x2 - bitmap2?.width!!.div(2)
        y2 = y2 - bitmap2?.height!!.div(2)
        canvas.drawBitmap(bitmap2, x2, y2,paint)


        /**
         * Draw 3rd Image
         */

        x3 = x3 - bitmap3?.width!!.div(2)
        y3 = y3 - bitmap3?.height!!.div(2)
        canvas.drawBitmap(bitmap3, x3, y3,paint)



        /**
         * Draw 4th Image
         */

        x4 = x4 - (bitmap4!!.width).div(2)
        y4 = y4 - (bitmap4!!.height).div(2)
        canvas.drawBitmap(bitmap4, x4, y4,paint)


    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> clickOnBitmap(event)

        }
        return super.onTouchEvent(event)
    }

    private fun clickOnBitmap(event: MotionEvent) {

        val xEnd1 = x1 + (bitmap1!!.width)
        val yEnd1 = y1 + (bitmap1!!.height)

        oval.set(center_x - radius,
                center_y - radius,
                center_x + radius,
                center_y + radius)




        if (event.x >= x1 && event.x <= xEnd1 && event.y >= y1 && event.y <= yEnd1) {

            check = true


            if (pos_b1 == 1) {
                var ax1: ValueAnimator = doAnim(angle1.toFloat(), sweepAngle1, 1)

                var ax2: ValueAnimator = doAnim(angle2.toFloat(), 45f, 2)
                var ax3: ValueAnimator = doAnim(angle3.toFloat(), 45f, 3)
                var ax4: ValueAnimator = doAnim(angle4.toFloat(), 135f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 4
                pos_b2 = 1
                pos_b3 = 2
                pos_b4 = 3
            }

            if(pos_b1 == 2) {


                var ax1: ValueAnimator = doAnim(angle2.toFloat() ,180f, 1)

                var ax2: ValueAnimator = doAnim(angle3.toFloat(), 90f, 2)
                var ax3: ValueAnimator = doAnim(angle4.toFloat(), 180f, 3)
                var ax4: ValueAnimator = doAnim(angle1.toFloat(), 270f,4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {

                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 4
                pos_b2 = 1
                pos_b3 = 2
                pos_b4 = 3

                }

            if(pos_b1 == 3) {


                var ax1: ValueAnimator = doAnim(angle3.toFloat(), 225f, 1)

                var ax2: ValueAnimator = doAnim(angle4.toFloat(), 225f, 2)
                var ax3: ValueAnimator = doAnim(angle1.toFloat(), 315f, 3)
                var ax4: ValueAnimator = doAnim(angle2.toFloat(), 315f,4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 4
                pos_b3 = 2
                pos_b2 = 1
                pos_b4 = 3

            }

        }





        val xEnd2 = x2 + (bitmap2!!.width)
        val yEnd2 = y2 + (bitmap2!!.height)


        if (event.x >= x2 && event.x <= xEnd2 && event.y >= y2 && event.y <= yEnd2) {

            Toast.makeText(this.context.applicationContext, "Image2 Clicked", Toast.LENGTH_SHORT).show()
            check =true
            if(pos_b2 == 2) {


                var ax1: ValueAnimator = doAnim(angle1.toFloat(), 270f, 1)

                var ax2: ValueAnimator = doAnim(angle2.toFloat(), 180f, 2)

                var ax3: ValueAnimator = doAnim(angle3.toFloat(), 90f, 3)
                var ax4: ValueAnimator = doAnim(angle4.toFloat(), 180f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {



                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 3
                pos_b2 = 4
                pos_b3 = 1
                pos_b4 = 2
            }

            if(pos_b2 == 1) {

                Toast.makeText(this.context.applicationContext, "Image2 Clicked"+ 1, Toast.LENGTH_SHORT).show()
                var ax1: ValueAnimator = doAnim(angle4.toFloat(), 135f, 1)

                var ax2: ValueAnimator = doAnim(angle1.toFloat(), 135f, 2)
                var ax3: ValueAnimator = doAnim(angle2.toFloat(), 45f, 3)
                var ax4: ValueAnimator = doAnim(angle3.toFloat(), 45f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b2 = 4
                pos_b1 = 3
                pos_b3 = 1
                pos_b4 = 2
            }

            if(pos_b2 == 3) {


                var ax1: ValueAnimator = doAnim(angle2.toFloat(), 315f, 1)

                var ax2: ValueAnimator = doAnim(angle3.toFloat(), 225f, 2)
                var ax3: ValueAnimator = doAnim(angle4.toFloat(), 225f, 3)
                var ax4: ValueAnimator = doAnim(angle1.toFloat(), 315f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {

                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b2 = 4
                pos_b1 = 3
                pos_b3 = 1
                pos_b4 = 2
            }



        }

        val xEnd3 = x3 + (bitmap3!!.width)
        val yEnd3 = y3 + (bitmap3!!.height)


        if (event.x >= x3 && event.x <= xEnd3 && event.y >= y3 && event.y <= yEnd3) {
            // clicked on image 3
            Toast.makeText(this.context.applicationContext, "Image3 Clicked", Toast.LENGTH_SHORT).show()

            check =true
            if(pos_b3 == 3) {

                var ax1: ValueAnimator = doAnim(angle3.toFloat(), 225f, 3)

                var ax2: ValueAnimator = doAnim(angle1.toFloat(), 315f,1)
                var ax3: ValueAnimator = doAnim(angle2.toFloat(), 315f, 2)
                var ax4: ValueAnimator = doAnim(angle4.toFloat(), 225f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 2
                pos_b2 = 3
                pos_b3 = 4
                pos_b4 = 1
            }

            if(pos_b3 == 1) {

                var ax1: ValueAnimator = doAnim(angle1.toFloat(), 135f, 3)

                var ax2: ValueAnimator = doAnim(angle3.toFloat(), 45f,1)
                var ax3: ValueAnimator = doAnim(angle4.toFloat(), 135f, 2)
                var ax4: ValueAnimator = doAnim(angle2.toFloat(), 45f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 2
                pos_b2 = 3
                pos_b3 = 4
                pos_b4 = 1
            }

            if(pos_b3 == 2) {

                var ax1: ValueAnimator = doAnim(angle2.toFloat(), 180f, 3)

                var ax2: ValueAnimator = doAnim(angle4.toFloat(), 180f,1)
                var ax3: ValueAnimator = doAnim(angle1.toFloat(), 270f, 2)
                var ax4: ValueAnimator = doAnim(angle3.toFloat(), 90f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 2
                pos_b2 = 3
                pos_b3 = 4
                pos_b4 = 1
            }



        }

        val xEnd4 = x4 + (bitmap3!!.width)
        val yEnd4 = y4 + (bitmap3!!.height)

        if (event.x >= x4 && event.x <= xEnd4 && event.y >= y4 && event.y <= yEnd4) {
            // clicked on image 3
            Toast.makeText(this.context.applicationContext, "Image3 Clicked", Toast.LENGTH_SHORT).show()

            check =true
            if(pos_b4 == 1) {

                var ax1: ValueAnimator = doAnim(angle4.toFloat(), 135f, 3)

                var ax2: ValueAnimator = doAnim(angle2.toFloat(), 45f,1)
                var ax3: ValueAnimator = doAnim(angle3.toFloat(), 45f, 2)
                var ax4: ValueAnimator = doAnim(angle1.toFloat(), 135f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 1
                pos_b2 = 2
                pos_b3 = 3
                pos_b4 = 4
            }


            if(pos_b4 == 2) {

                var ax1: ValueAnimator = doAnim(angle2.toFloat(), 180f, 4)

                var ax2: ValueAnimator = doAnim(angle3.toFloat(), 90f,1)
                var ax3: ValueAnimator = doAnim(angle4.toFloat(), 180f, 2)
                var ax4: ValueAnimator = doAnim(angle1.toFloat(), 270f, 3)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 1
                pos_b2 = 2
                pos_b3 = 3
                pos_b4 = 4
            }


            if(pos_b4 == 3) {

                var ax1: ValueAnimator = doAnim(angle2.toFloat(), 315f, 3)

                var ax2: ValueAnimator = doAnim(angle4.toFloat(), 225f,1)
                var ax3: ValueAnimator = doAnim(angle1.toFloat(), 315f, 2)
                var ax4: ValueAnimator = doAnim(angle3.toFloat(), 225f, 4)

                val mAnimatorSet = AnimatorSet()
                mAnimatorSet.playTogether(ax1, ax2, ax3, ax4)
                mAnimatorSet.duration = 2000 //set duration for animations
                mAnimatorSet.start()
                mAnimatorSet.addListener(object : AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                        // ...
                    }

                    override fun onAnimationEnd(animation: Animator) {


                    }

                    override fun onAnimationCancel(animation: Animator) {
                        // ...
                    }
                })


                pos_b1 = 1
                pos_b2 = 2
                pos_b3 = 3
                pos_b4 = 4
            }


        }


    }


    fun doAnim(startAngle: Float, sweepAngle:Float,b1:Int):ValueAnimator {

        val point = floatArrayOf(0f, 0f)

        var ax: ValueAnimator? = null
        ax = ValueAnimator.ofFloat(0f, 1f)
        var myPath = Path()
        myPath.arcTo(oval, startAngle, sweepAngle, false)
        val pm = PathMeasure(myPath, false)
        ax.setDuration(5000L)
        ax.setInterpolator(AccelerateDecelerateInterpolator())

        ax.addUpdateListener(@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
        object : ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {

                val valu = animation!!.animatedValue as Float
                pm.getPosTan(pm.getLength() * valu, point, null)
                if(b1 == 1) {
                    x1 = (point[0])
                    y1 = point[1]
                } else if(b1 == 2){
                        x2 = (point[0])
                        y2 = point[1]
                    }  else if(b1 == 3){
                        x3 = (point[0])
                        y3 = point[1]
                    }
                    else if(b1 == 4){
                        x4 = (point[0])
                        y4 = point[1]
                    }

                invalidate()
            }
        })

        return ax
    }


}