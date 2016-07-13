package androidsamples.customquotespan;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.LeadingMarginSpan;


public class CustomQuoteSpan implements LeadingMarginSpan {


    private static final int GAP_WIDTH = 50;
    private static final String LEFT_QUOTE = "\u201C";
    private TextPaint textPaint = new TextPaint();

    public CustomQuoteSpan()
    {
        init();
    }

    public void init()
    {

        textPaint.setColor(Color.RED);
        textPaint.setTextSize(75f);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return  GAP_WIDTH;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir,
                                  int top, int baseline, int bottom,
                                  CharSequence text, int start, int end,
                                  boolean first, Layout layout) {

        if(((Spanned)text).getSpanStart(this) == start) {
            c.drawText(LEFT_QUOTE, x, top + textPaint.getTextSize(), textPaint);

        }
    }
}
