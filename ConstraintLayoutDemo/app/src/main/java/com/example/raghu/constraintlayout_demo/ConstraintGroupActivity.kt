package com.example.raghu.constraintlayout_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_constraint_group.*

class ConstraintGroupActivity : AppCompatActivity() {

    private var flag = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_group)

        hide.setOnClickListener({
            if(!flag) {
                group.visibility = View.GONE
                flag = true
            }else {
                group.visibility = View.VISIBLE
                flag=false
            }
        })
    }
}
