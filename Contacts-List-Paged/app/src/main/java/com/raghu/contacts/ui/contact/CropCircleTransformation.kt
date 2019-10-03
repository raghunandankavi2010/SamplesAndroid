package com.raghu.contacts.ui.contact

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.nio.charset.Charset
import java.security.MessageDigest


class CropCircleTransformation: BitmapTransformation() {

    private val  id = "com.raghu.contacts.CropImageToCircle"
    private val  bytes = id.toByteArray(Charset.forName("UTF-8"))
    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(bytes)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight);
    }

    override fun equals(other: Any?): Boolean {
        return other is CropCircleTransformation
    }
    override fun hashCode(): Int {
        return id.hashCode()
    }
}