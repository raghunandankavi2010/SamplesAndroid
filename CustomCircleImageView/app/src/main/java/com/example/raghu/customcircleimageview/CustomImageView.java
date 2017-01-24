package com.example.raghu.customcircleimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Raghu on 30-12-2016.
 */

public class CustomImageView extends View
{


    private String name = "8";
    private int mTextWidth, mTextHeight; // Our calculated text bounds
    private Paint mTextPaint,mCirlcePaint,mRectPaint,mInnerCirclePaint;
    private int centerX,centerY;
    private Rect textBounds;

    private int radius = 600;

    private int width_arc = 50;

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init()
    {

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.GRAY);
        mRectPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(200f);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(20f);
        mTextPaint.setStyle(Paint.Style.FILL);

        mCirlcePaint = new Paint();
        mCirlcePaint.setStyle(Paint.Style.FILL);
        mCirlcePaint.setColor(Color.GREEN);

        mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);
        mInnerCirclePaint.setColor(Color.WHITE);

        // Now lets calculate the size of the text
        textBounds = new Rect();
        mTextPaint.getTextBounds(name, 0, name.length(), textBounds);
        mTextWidth = Math.round(mTextPaint.measureText(name.toString())); // Use measureText to calculate width
        mTextHeight = textBounds.height(); // Use height from getTextBounds()
        //mBounds = textBounds;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Later when you draw...
        canvas.drawCircle(centerX,centerY,radius,mCirlcePaint);

        drawInnerCircle(canvas);
        canvas.drawText(name, // Text to display
                centerX - (mTextWidth / 2f),
                centerY + (mTextHeight / 2f),
                mTextPaint
        );
    }


    private void drawInnerCircle(Canvas c) {

        c.drawCircle(getWidth() / 2, getHeight() / 2, (radius/2) + width_arc
               , mInnerCirclePaint);
    }
}
