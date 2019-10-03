package com.raghu.contacts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raghu.contacts.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        name.text = intent.getStringExtra("name")
        number.text = intent.getStringExtra("number")
    }
}