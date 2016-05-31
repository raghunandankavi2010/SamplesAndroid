package com.example.searchiconmorphedsearchview;

import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private AnimatedVectorDrawable barToSearch;
    private int duration;
    private boolean expanded = false;
    private Interpolator interp;
    private ImageView iv;
    private float offset;
    private AnimatedVectorDrawable searchToBar;
    private TextView text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.iv = (ImageView) findViewById(R.id.search);
        this.text = (TextView) findViewById(R.id.text);
        this.searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);
        this.barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);
        this.interp = AnimationUtils.loadInterpolator(this, 17563662);
        this.duration = 5000;
        this.offset = -71.0f * ((float) ((int) getResources().getDisplayMetrics().scaledDensity));
        this.iv.setTranslationX(this.offset);
    }

    public void animate(View view) {
        if (this.expanded) {
            this.iv.setImageDrawable(this.barToSearch);
            this.barToSearch.start();
            this.iv.animate().translationX(this.offset).setDuration((long) this.duration).setInterpolator(this.interp);
            this.text.setAlpha(0.0f);
        } else {
            this.iv.setImageDrawable(this.searchToBar);
            this.searchToBar.start();
            this.iv.animate().translationX(0.0f).setDuration((long) this.duration).setInterpolator(this.interp);
            this.text.animate().alpha(1.0f).setStartDelay((long) (this.duration - 100)).setDuration(100).setInterpolator(this.interp);
        }
        this.expanded = !this.expanded;
    }
}
