package com.example.raghu.myapplication

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController

class MainActivity : AppCompatActivity() {



    lateinit var toolbar: Toolbar
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)

         navController = Navigation.findNavController(this,R.id.my_nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this,navController)

        setupWithNavController(findViewById<BottomNavigationView>(R.id.navigation),  navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()

    }

}
