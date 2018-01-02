package com.ecommerce.ctdib2brecommerce.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.ecommerce.ctdib2brecommerce.R
import com.ecommerce.ctdib2brecommerce.databinding.ActivityRegistrationBinding
import com.ecommerce.ctdib2brecommerce.delegates.SetContentView


class RegistrationActivity : AppCompatActivity() {

    private val binding: ActivityRegistrationBinding by SetContentView<AppCompatActivity, ActivityRegistrationBinding>(R.layout.activity_registration)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbar:Toolbar = binding.toolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title ="Registration"
        binding.submit.setOnClickListener( { v -> validate() } )



    }

    private fun validate() {

    }
}
