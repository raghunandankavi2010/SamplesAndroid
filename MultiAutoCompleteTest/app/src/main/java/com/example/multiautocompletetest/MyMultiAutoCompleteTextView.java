package com.example.multiautocompletetest;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

class MyMultiAutoCompleteTextView extends androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView {

    private Context context;
    public MyMultiAutoCompleteTextView(Context context) {
        super(context);
        this.context = context;
    }

    public MyMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyMultiAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void replaceText(CharSequence text) {
        Log.d("Log", "replaceText " + text.getClass() + " " + text);
        super.replaceText(getSpanned(text.toString()));
    }

    private Spanned getSpanned(String name) {
        TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.test, null);
        tv.setText(name);
        SpannableStringBuilder sb = new SpannableStringBuilder(name);
        sb.setSpan(new ForegroundColorSpan(Color.BLUE), 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}


