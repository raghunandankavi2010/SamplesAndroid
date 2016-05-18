package test.raghu.test;

import android.support.annotation.ColorInt;

/**
 * Created by Raghunandan on 18-05-2016.
 */
public class AnnotationSpan {

    private int color;

    public AnnotationSpan(@ColorInt int color)
    {
        this. color = color;
    }

    public int getColor() {
        return color;
    }
}
