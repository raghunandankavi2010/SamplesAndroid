package com.demo.sticker;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
  private int space;

  public GridSpaceItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(
      Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
    outRect.left = space;
    outRect.right = space;
    outRect.bottom = space;

    // Add top margin only for the first item to avoid double space between items
    if (parent.getChildLayoutPosition(view) == 0) {
      outRect.top = space;
    } else {
      outRect.top = 0;
    }
  }
}
