package com.example.raghu.customcircletextpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by raghu on 18/4/17.
 */

public class CustomCircleView extends View {

    private String QUOTE= "012345678910";
    private Path circle;
    private Paint mCirlcePaint;
    private Paint tPaint;
    private Rect textBounds;
    private int mTextWidth, mTextHeight,centerX,centerY;


    public CustomCircleView(Context context) {
        super(context);
        circle = new Path();

        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tPaint.setColor(Color.BLACK);
        tPaint.setTextSize(40f);
        textBounds = new Rect();

        tPaint.getTextBounds(QUOTE, 0, QUOTE.length(), textBounds);
        mTextWidth = Math.round(tPaint.measureText(QUOTE.toString())); // Use measureText to calculate width
        mTextHeight = textBounds.height(); // Use height from getTextBounds()

        mCirlcePaint = new Paint();
        mCirlcePaint.setStyle(Paint.Style.FILL);
        mCirlcePaint.setColor(Color.GREEN);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w/2;
        centerY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.restore();
        float startAngle = 0;
        float sweepAngle = 0;

        float xPos =  centerX - (mTextWidth / 2f);
        float yPos = centerY + (mTextHeight / 2f);
        //int yPos = (int) ((canvas.getHeight() / 3) - ((tPaint.descent() + tPaint.ascent()) / 3)) ;
        float angle = (float) ((startAngle + sweepAngle / 2) * Math.PI / 180);
        float stopX = (float) (centerX + 150 * Math.cos(angle));
        float stopY = (float) (centerY + 150 * Math.sin(angle));
        canvas.drawCircle(centerX,centerY,150,mCirlcePaint);
        circle.addCircle(centerX, centerY, 150, Path.Direction.CW);
        canvas.drawTextOnPath(QUOTE, circle, (centerX)-(mTextWidth / 2f), 0, tPaint);
        //canvas.drawTextOnPath(QUOTE1, circle, (centerX)-(mTextWidth / 2f), 100, tPaint);

        //canvas.drawLine(centerX,centerY,stopX,stopY,tPaint);

        /*float startAngle1 = 180;
        float sweepAngle1 = 0;
        float angle1 = (float) ((startAngle1 + sweepAngle1 / 2) * Math.PI / 180);
        float stopX1 = (float) (centerX + 150 * Math.cos(angle1));
        float stopY1 = (float) (centerY + 150 * Math.sin(angle1));
        canvas.drawLine(centerX,centerY,stopX1,stopY1,tPaint);*/

/*
        float  hoffset = 270 - (mTextWidth / 2f);
        float voffset = 75 - (mTextHeight / 2f);
        canvas.drawCircle(xPos,yPos,200,mCirlcePaint);

        QUOTE="";*/

    }



}