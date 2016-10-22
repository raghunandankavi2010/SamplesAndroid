package example.raghunandan.fadeinbuttontext;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Property;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView button = (TextView) findViewById(R.id.button);
        String text = button.getText().toString();

        final CustomSpan span = new CustomSpan();
        final SpannableString spannableString = new SpannableString(text);

        int start = 0;
        int end = text.length();
        spannableString.setSpan(span, start, end, 0);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(
                span, FADE_INT_PROPERTY, 0, 255);
        objectAnimator.setEvaluator(new IntEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.setText(spannableString);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                objectAnimator.setDuration(1000);
                objectAnimator.start();

            }
        });
    }

    private static final Property<CustomSpan, Integer> FADE_INT_PROPERTY
            = new Property<CustomSpan, Integer>(Integer.class, "FADE_INT_PROPERTY") {

        @Override
        public void set(CustomSpan span, Integer value) {
            span.setAlpha(value);
        }
        @Override
        public Integer get(CustomSpan object) {
            return object.getAlpha();
        }
    };
}
