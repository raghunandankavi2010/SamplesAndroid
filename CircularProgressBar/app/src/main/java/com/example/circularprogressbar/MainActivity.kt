package com.example.circularprogressbar

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.circularprogressbar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        binding.circularProgressView.maxProgress = 200

        binding.circle.text = "8"
        binding.circle.circleColor = Color.BLUE
        binding.circle.textColor = Color.GREEN

        // Using ObjectAnimator
        val anim = ObjectAnimator.ofInt(binding.circularProgressView, "percentage", 0, 100)
        anim.apply {
            duration = 2000
            interpolator = LinearInterpolator()
            start()
        }

        // Using ValueAnimator
       /*ValueAnimator.ofInt(0, 100).apply {
            addUpdateListener { updatedAnimation ->
                val progress = updatedAnimation.animatedValue as Int
                binding.circularProgressView.setPercentage(progress)

            }
            interpolator = LinearInterpolator()
            duration = 10000
            start()
        }*/

    }
}