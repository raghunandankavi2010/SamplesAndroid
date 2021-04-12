package com.example.cirlceimageview

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val iv = findViewById<CircleImageView>(R.id.iv)
        iv.setImageDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.download
            )
        )
        iv.setBadgeValue(19.2.toFloat())
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(16.toFloat())
                .setMaxBadgeValue(999)
                .setBadgeBackground(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.bkg, null
                    )
                )
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4)

    }
}