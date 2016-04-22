package com.indiainnovates.pucho.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.indiainnovates.pucho.R;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);


        int spacing = view.getContext().getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        //int spacing = 16;
        int halfSpacing = (spacing / 2);

        int childCount = parent.getLayoutManager().getItemCount();
        int childIndex = parent.getChildLayoutPosition(view);
        int spanCount = getTotalSpan(view, parent);
        int spanIndex = childIndex % spanCount;

        /* INVALID SPAN */
        if (spanCount < 1) return;

        outRect.top = halfSpacing;
        outRect.bottom = halfSpacing;
        outRect.left = halfSpacing;
        outRect.right = halfSpacing;

        if (isTopEdge(childIndex, spanCount)) {
            //outRect.top = spacing;
            outRect.top = childIndex < spanCount ? spacing : 0;
        }

        if (isLeftEdge(spanIndex, spanCount)) {
            outRect.left = spacing;
        }

        if (isRightEdge(spanIndex, spanCount)) {
            outRect.right = spacing;
        }

        if (isBottomEdge(childIndex, childCount, spanCount,spanIndex)) {
            outRect.bottom = spacing;
        }
    }

    protected int getTotalSpan(View view, RecyclerView parent) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanCount();
        }
        return -1;
    }

    protected boolean isLeftEdge(int spanIndex, int spanCount) {

        return spanIndex == 0;
    }

    protected boolean isRightEdge(int spanIndex, int spanCount) {

        return spanIndex == spanCount - 1;
    }

    protected boolean isTopEdge(int childIndex, int spanCount) {

        return childIndex < spanCount;
    }

    protected boolean isBottomEdge(int childIndex, int childCount, int spanCount,int spanIndex) {

        return childIndex >= childCount - spanCount + spanIndex;
    }

    public int dpToPx(int dp,Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}