package com.example.circularprogressbar

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.circularprogressbar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        ValueAnimator.ofInt(0, 100).apply {
            addUpdateListener { updatedAnimation ->
                val progress = updatedAnimation.animatedValue as Int
                binding.circularProgressView.setPercentage(progress)
            }
            interpolator = LinearInterpolator()
            duration = 10000
            start()
        }
    }
}