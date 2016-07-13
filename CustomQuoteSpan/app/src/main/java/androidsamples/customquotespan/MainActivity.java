package androidsamples.customquotespan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.QuoteSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        this.setSupportActionBar(toolbar);

        tv =(TextView) findViewById(R.id.textView);
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append("Hello World. My Try on Custom Quote Span. .................................." +
                "................................" +
                "......................................" +
                "....................");
        ssb.setSpan(new CustomQuoteSpan(),0,ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Spannable text = replaceQuoteSpans(ssb);
        tv.setText(text);
    }


    public static Spannable replaceQuoteSpans(Spannable text)
    {

        QuoteSpan[] spans =  text.getSpans(0,text.length(),QuoteSpan.class);

        for(QuoteSpan span : spans)
        {
            int start = text.getSpanStart(span);
            int end = text.getSpanEnd(span);
            text.removeSpan(span);
            text.setSpan(new CustomQuoteSpan(),start,end,0);
        }

        return text;
    }
}

