package com.example.raghunandan.fadeinspan;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Property;
import android.widget.TextView;

import static android.R.attr.duration;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) this.findViewById(R.id.text);
        String text = textView.getText().toString();

        final FadeInSpan span = new FadeInSpan();

        final SpannableString mSpannableString = new SpannableString(text);
        mSpannableString.setSpan(span, 0, mSpannableString.length(), 0);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(
                span, FADE_INT_PROPERTY, 0, 255);
        objectAnimator.setEvaluator(new IntEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(mSpannableString);
            }
        });
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    /* Fade In */
    private static final Property<FadeInSpan, Integer> FADE_INT_PROPERTY
            = new Property<FadeInSpan, Integer>(Integer.class, "FADE_INT_PROPERTY") {

        @Override
        public void set(FadeInSpan span, Integer value) {
            span.setAlpha(value);
        }
        @Override
        public Integer get(FadeInSpan object) {
            return object.getAlpha();
        }
    };
}
