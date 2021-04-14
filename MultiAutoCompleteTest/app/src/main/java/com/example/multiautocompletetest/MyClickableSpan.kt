package com.example.multiautocompletetest

import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView


class MyClickableSpan() : ClickableSpan() {

    private lateinit var onClick: OnClickItem
    public interface OnClickItem {
        fun onClick(clickedItem: String)
    }

    override fun updateDrawState(ds: TextPaint) { // override updateDrawState
        ds.isUnderlineText = false // set to false to remove underline
    }

    override fun onClick(widget: View) {
        val tv = widget as TextView
        val span = tv.text as Spanned
        val start = span.getSpanStart(this)
        val end = span.getSpanEnd(this)
        Log.i("Start- End:", "$start $end")
        val text = span.subSequence(start, end)
        onClick.onClick(text.toString())
        //clickedItem(text.toString())
    }

    fun setOnClickListener(onClickItem: OnClickItem) {
       onClick = onClickItem
    }
}
