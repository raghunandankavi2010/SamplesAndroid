package com.india.innovates.pucho.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;

import com.india.innovates.pucho.adapters.FeedAdapter;
import com.india.innovates.pucho.models.FeedModel;
import com.india.innovates.pucho.utils.Utility;

import java.util.List;


/**
 * Created by Raghunandan on 16-03-2016.
 */
public class FeedItemAnimator extends DefaultItemAnimator {


    private int lastAddAnimatedItem = -2;


    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags, @NonNull List<Object> payloads) {
        if (changeFlags == FLAG_CHANGED) {
            for (Object payload : payloads) {
                if (payload instanceof FeedModel) {
                    return new FeedItemHolderInfo((FeedModel) payload);
                }
            }
        }

        return super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getItemViewType() == FeedAdapter.VIEW_ITEM) {
            if (viewHolder.getLayoutPosition() > lastAddAnimatedItem) {
                lastAddAnimatedItem++;
                runEnterAnimation((FeedAdapter.ViewHoldera) viewHolder);
                return false;
            }
        }

        dispatchAddFinished(viewHolder);
        return false;
    }

    private void runEnterAnimation(final FeedAdapter.ViewHoldera holder) {
        final int screenHeight = Utility.getScreenHeight(holder.cv.getContext());
        holder.cv.setTranslationY(screenHeight);
        holder.cv.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(holder);
                    }
                })
                .start();
    }



    public  class FeedItemHolderInfo extends ItemHolderInfo {
        public FeedModel updateAction;

        public FeedItemHolderInfo(FeedModel updateAction) {
            this.updateAction = updateAction;
        }
    }
}

