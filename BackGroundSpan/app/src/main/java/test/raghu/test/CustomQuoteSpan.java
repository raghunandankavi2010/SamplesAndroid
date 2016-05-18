package test.raghu.test;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LeadingMarginSpan;

/**
 * Created by Raghunandan on 18-05-2016.
 */
public class CustomQuoteSpan implements LeadingMarginSpan{

    public static final int GAP = 200;
    public static final int QUOTE_SIZE = 300;
    public static final float SCALE= 1.8f;
    public static final String LEFT_QUOTE = "\u201C";
    private TextPaint textPaint = new TextPaint();


    @Override
    public int getLeadingMargin(boolean first) {
        return GAP;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

        if(((Spanned) text).getSpanStart(this) == start)
        {
            c.drawText(LEFT_QUOTE,x, top + QUOTE_SIZE, textPaint);
        }
    }
}
