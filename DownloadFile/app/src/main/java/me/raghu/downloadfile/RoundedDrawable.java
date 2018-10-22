package me.raghu.downloadfile;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class RoundedDrawable extends Drawable {

    private Bitmap bitmap;
    private int side;
    private Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path maskPath = new Path();
    private float maskRadii;

    public RoundedDrawable(Bitmap wrappedBitmap, float radii) {
        bitmap = wrappedBitmap;
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10);
        borderPaint.setColor(0xcc220088);
        this.maskRadii = radii;
        side = Math.min(bitmap.getWidth(), bitmap.getHeight());
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        Matrix matrix = new Matrix();
        RectF src = new RectF(0, 0, side, side);
        src.offset((bitmap.getWidth() - side) / 2f, (bitmap.getHeight() - side) / 2f);
        RectF dst = new RectF(bounds);
        dst.inset(borderPaint.getStrokeWidth(), borderPaint.getStrokeWidth());
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        maskPaint.setShader(shader);
        matrix.mapRect(src);

        maskPath.reset();
        maskPath.addRoundRect(src, maskRadii,maskRadii, Path.Direction.CW);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(maskPath, maskPaint);
    }

    @Override public void setAlpha(int alpha) {}
    @Override public void setColorFilter(ColorFilter cf) {}
    @Override public int getOpacity() {return PixelFormat.TRANSLUCENT;}
}