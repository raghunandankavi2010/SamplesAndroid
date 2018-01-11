package com.example.raghu.customviewmorphing;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by raghu on 11/1/18.
 */

public class CustomView extends View {

    private Paint paint;
    private int radius;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        radius=0;
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth()/2;
        int height = getHeight()/2;


        int leftTopX = width - 150;
        int leftTopY = height - 150;

        int rightBotX = width + 150;
        int rightBotY = width + 150;

        canvas.drawRoundRect(leftTopX, leftTopY, rightBotX, rightBotY, radius, radius, paint);



}

public void animateView(){
    ValueAnimator animator = ValueAnimator.ofInt(0, 300);
    animator.setDuration(2000);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            radius = (int) animation.getAnimatedValue();
            invalidate();
        }
    });
    animator.start();
}
}
