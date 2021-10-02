package com.example.cirlceimageview


import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils


class MainActivity : AppCompatActivity() {
    @SuppressLint("UnsafeExperimentalUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val iv = findViewById<CircleImageView>(R.id.iv)
        val imageView = findViewById<CircleImageView>(R.id.imageView)
        iv.setImageDrawable(
                ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.download
                )
        )

        val iv2 = findViewById<CircleImageView>(R.id.iv2)
        iv2.setImageDrawable(
                ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.download
                )
        )
        val badgeDrawable = BadgeDrawable.create(this)
        badgeDrawable.isVisible = true
        badgeDrawable.number = 15
        BadgeUtils.attachBadgeDrawable(badgeDrawable, iv2)

        val text = "+5"
        val drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .width(80.dp)
                .height(80.dp)
                .endConfig()
                .buildRound(text, Color.parseColor("#8FAE5D"))
        imageView.setImageDrawable(drawable)


        /*  iv.setBadgeValue(9.toFloat())
                  .setBadgeOvalAfterFirst(true)
                  .setBadgeTextSize(16.toFloat())
                  .setMaxBadgeValue(999)
                 *//* .setBadgeBackground(
                        ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bkg, null
                        )
                )*//*
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4)*/

    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}