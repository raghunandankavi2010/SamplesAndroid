package com.example.raghu.drawersample;

/**
 * Created by raghu on 14/11/17.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;


public class ScrimInsetsRelativeLayout extends RelativeLayout  {

    private Drawable mInsetForeground;
    private Rect mInsets;
    private Rect mTempRect = new Rect();


    private boolean mTintStatusBar = true;
    private boolean mTintNavigationBar = true;
    private boolean mSystemUIVisible = true;

    public ScrimInsetsRelativeLayout(Context context) {
        this(context, null);
    }

    public ScrimInsetsRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimInsetsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ScrimInsetsRelativeLayout, defStyleAttr,
                R.style.Widget_Materialize_ScrimInsetsRelativeLayout);
        mInsetForeground = a.getDrawable(R.styleable.ScrimInsetsRelativeLayout_sirl_insetForeground);
        a.recycle();
        setWillNotDraw(true); // No need to draw until the insets are adjusted

        ViewCompat.setOnApplyWindowInsetsListener(this,
                new android.support.v4.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v,
                                                                  WindowInsetsCompat insets) {
                        if (null == mInsets) {
                            mInsets = new Rect();
                        }
                        mInsets.set(insets.getSystemWindowInsetLeft(),
                                insets.getSystemWindowInsetTop(),
                                insets.getSystemWindowInsetRight(),
                                insets.getSystemWindowInsetBottom());
                        setWillNotDraw(mInsetForeground == null);
                        ViewCompat.postInvalidateOnAnimation(ScrimInsetsRelativeLayout.this);

                        return insets.consumeSystemWindowInsets();
                    }
                });
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsetForeground != null) {
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            if (!mSystemUIVisible) {
                mInsets.top = 0;
                mInsets.right = 0;
                mInsets.bottom = 0;
                mInsets.left = 0;
            }

            // Top
            if (mTintStatusBar) {
                mTempRect.set(0, 0, width, mInsets.top);
                mInsetForeground.setBounds(mTempRect);
                mInsetForeground.draw(canvas);
            }

            // Bottom
            if (mTintNavigationBar) {
                mTempRect.set(0, height - mInsets.bottom, width, height);
                mInsetForeground.setBounds(mTempRect);
                mInsetForeground.draw(canvas);
            }
            // Left
            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            // Right
            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInsetForeground != null) {
            mInsetForeground.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsetForeground != null) {
            mInsetForeground.setCallback(null);
        }
    }

    public boolean isSystemUIVisible() {
        return mSystemUIVisible;
    }

    public void setSystemUIVisible(boolean systemUIVisible) {
        this.mSystemUIVisible = systemUIVisible;
    }

}