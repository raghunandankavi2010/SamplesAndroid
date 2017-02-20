package com.example.raghu.flowlayoutdemo;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Raghu on 20-02-2017.
 */

public class UnderLineTextView extends TextView {
    public UnderLineTextView(Context context) {
        super(context);
    }

    public UnderLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        // code to check text for null omitted
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        super.setText(content, BufferType.SPANNABLE);

    }
}
