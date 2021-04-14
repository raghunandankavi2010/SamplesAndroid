package com.example.multiautocompletetest;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.widget.TextView;


class ViewReplacementSpan extends DynamicDrawableSpan {
    private View v;
    private Drawable drawable;

    public ViewReplacementSpan(View v) {
        super(ALIGN_BOTTOM);
        this.v = v;
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(spec, spec);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        drawable = new SpanDrawable();
        drawable.setBounds(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    class SpanDrawable extends Drawable {
        @Override
        public void draw(Canvas canvas) {
            canvas.clipRect(getBounds());
            v.draw(canvas);
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}