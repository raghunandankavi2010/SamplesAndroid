package com.example.raghu.myapplication

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.my_nav_host_fragment)
        setupActionBarWithNavController(navController)

        findViewById<BottomNavigationView>(R.id.navigation)
                .setupWithNavController(navController)

    }


    override fun onSupportNavigateUp()
            = findNavController(R.id.my_nav_host_fragment).navigateUp()

}
