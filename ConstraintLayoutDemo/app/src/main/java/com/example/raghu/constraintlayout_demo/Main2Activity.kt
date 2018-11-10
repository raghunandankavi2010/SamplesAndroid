package com.example.raghu.constraintlayout_demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        mainact.setOnClickListener({
            val intent = Intent(this@Main2Activity,MainActivity::class.java)
            startActivity(intent)
        })

        guideLineact.setOnClickListener({
            val intent = Intent(this@Main2Activity,GuideLineActivity::class.java)
            startActivity(intent)
        })

        circurlaract.setOnClickListener({
            val intent = Intent(this@Main2Activity,CircularConstraint::class.java)
            startActivity(intent)
        })

        constraintset.setOnClickListener({
            val intent = Intent(this@Main2Activity,ConstraintSetActivity::class.java)
            startActivity(intent)
        })

        barriers.setOnClickListener({
            val intent = Intent(this@Main2Activity,BarrierActivity::class.java)
            startActivity(intent)
        })

        cgroup.setOnClickListener({
            val intent = Intent(this@Main2Activity,ConstraintGroupActivity::class.java)
            startActivity(intent)
        })

        csetdemo2.setOnClickListener({
            val intent = Intent(this@Main2Activity,ConstraintSetDemo2::class.java)
            startActivity(intent)
        })
    }
}
