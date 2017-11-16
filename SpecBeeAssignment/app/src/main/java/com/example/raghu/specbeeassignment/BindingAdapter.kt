package com.example.raghu.specbeeassignment

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * Created by raghu on 16/11/17.
 */

class BindingAdapter {

   companion object {

   @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?) {

            if (TextUtils.isEmpty(url))
                return
            val cropOptions = RequestOptions().placeholder(R.mipmap.ic_launcher)


            val requestManager = Glide.with(imageView.context)

            try {
                if (!TextUtils.isEmpty(url)) {

                    requestManager.asBitmap()
                            .apply(cropOptions)
                            .load(url)
                            .into(imageView)

                } else {
                    requestManager.clear(imageView)
                    // remove the placeholder (optional); read comments below
                    imageView.setImageDrawable(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}