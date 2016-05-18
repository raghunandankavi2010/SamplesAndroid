package test.raghu.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Raghunandan on 18-05-2016.
 */
public class CustomTextView extends TextView
{

    private Spanned spannedText;
    private AnnotationSpan[] bgspans;
    private TextPaint paint;

    private int spanStart,spanEnd;
    private float spanStartX,spanEndX;
    private int lineStartNum,lineEndNum;

    private float leftx,rightx;

    private Paint.FontMetrics fontMetrics;

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        spannedText = (Spanned) getText();

        bgspans = spannedText.getSpans(0, spannedText.length(), AnnotationSpan.class);
        Layout layout = getLayout();

        paint = getPaint();
        fontMetrics = paint.getFontMetrics();
        for(AnnotationSpan span:bgspans) {
        paint.setColor(span.getColor());
            spanStart =  spannedText.getSpanStart(span);
            spanEnd = spannedText.getSpanEnd(span);

            spanStartX = getLayout().getPrimaryHorizontal(spanStart);
            spanEndX = getLayout().getPrimaryHorizontal(spanEnd);
            lineStartNum = getLayout().getLineForOffset(spanStart);
            lineEndNum = getLayout().getLineForOffset(spanEnd);



            for(int i =lineStartNum; i<=lineEndNum;i++){

                leftx = (i == lineStartNum) ? spanStartX : layout.getLineLeft(i);
                rightx = (i == lineEndNum) ? spanEndX : layout.getLineRight(i);
                canvas.drawRect(leftx, getLineTop(layout,i),rightx, getLineBottom(layout,i),paint);
            }
        }
        super.onDraw(canvas);
    }

    private float getLineTop( Layout layout, int line)
    {
        return layout.getLineBaseline(line) + fontMetrics.ascent;
    }

    private float getLineBottom( Layout layout, int line)
    {
        return layout.getLineBaseline(line) + fontMetrics.descent;
    }
}
