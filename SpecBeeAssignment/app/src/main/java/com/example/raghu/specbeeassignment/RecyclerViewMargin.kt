package com.example.raghu.specbeeassignment

import android.graphics.Rect
import android.support.annotation.IntRange
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by raghu on 16/11/17.
 */

class RecyclerViewMargin
/**
 * constructor
 * @param margin desirable margin size in px between the views in the recyclerView
 * @param columns number of columns of the RecyclerView
 */
(@param:IntRange(from = 0) private val margin: Int, @param:IntRange(from = 0) private val columns: Int) : RecyclerView.ItemDecoration() {

    /**
     * Set different margins for the items inside the recyclerView: no top margin for the first row
     * and no left margin for the first column.
     */
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State?) {

        val position = parent.getChildLayoutPosition(view)
        //set right margin to all
        outRect.right = margin
        //set bottom margin to all
        outRect.bottom = margin
        //we only add top margin to the first row
        if (position < columns) {
            outRect.top = margin
        }
        //add left margin only to the first column
        if (position % columns == 0) {
            outRect.left = margin
        }
    }
}