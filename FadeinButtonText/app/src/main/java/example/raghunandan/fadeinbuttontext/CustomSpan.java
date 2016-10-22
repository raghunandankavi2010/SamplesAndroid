package example.raghunandan.fadeinbuttontext;

/**
 * Created by Raghunandan on 23-10-2016.
 */

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;


public class CustomSpan extends CharacterStyle implements UpdateAppearance {

    private int alpha;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public CustomSpan() {

    }

    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setAlpha(alpha);

    }
}