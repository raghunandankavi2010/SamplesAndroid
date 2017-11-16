package com.example.raghu.specbeeassignment

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import java.util.ArrayList

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Text Entry"

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (!TextUtils.isEmpty(editText.text.toString())) {
                val intent = Intent()

                intent.putExtra("MESSAGE", editText.text.toString())
                setResult(MainActivity.Companion.REQUEST_RESULT,intent)
                finish()
            } else {
                Toast.makeText(this@SecondActivity.applicationContext, "Please Enter text ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
