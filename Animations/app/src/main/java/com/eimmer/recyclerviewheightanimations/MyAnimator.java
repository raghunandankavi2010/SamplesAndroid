package com.eimmer.recyclerviewheightanimations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eimmer.recyclerviewheightanimations.adapters.QuestionAdapter;

import java.util.HashMap;

/**
 * Created by Raghunandan on 17-02-2016.
 */
public  class MyAnimator extends DefaultItemAnimator {
    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    private HashMap<RecyclerView.ViewHolder, ValueAnimator> animatorMap = new HashMap<>();

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull final RecyclerView.ViewHolder newHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo preInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo postInfo) {
        ValueAnimator prevAnim = animatorMap.get(newHolder);
        if(prevAnim != null) {
            prevAnim.reverse();
            return false;
        }

        final ValueAnimator heightAnim;
        final QuestionAdapter.QuestionViewHolder vh = (QuestionAdapter.QuestionViewHolder) newHolder;
        final TextView tv = vh.getTvAnswer();

        if(vh.isExpanded()) {
            tv.measure(View.MeasureSpec.makeMeasureSpec(((View) tv.getParent()).getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.UNSPECIFIED);
            heightAnim = ValueAnimator.ofInt(tv.getHeight(), tv.getMeasuredHeight());
        } else {
            Paint.FontMetrics fm = tv.getPaint().getFontMetrics();
            heightAnim = ValueAnimator.ofInt(tv.getHeight(),tv.getMeasuredHeight() );
        }

        heightAnim.setDuration(getChangeDuration());
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.getLayoutParams().height = (Integer) heightAnim.getAnimatedValue();
                tv.requestLayout();
            }
        });

        heightAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchChangeFinished(vh, false);
                animatorMap.remove(newHolder);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

        animatorMap.put(newHolder, heightAnim);

        dispatchChangeStarting(newHolder, false);
        heightAnim.start();

        return false;
    }
}
