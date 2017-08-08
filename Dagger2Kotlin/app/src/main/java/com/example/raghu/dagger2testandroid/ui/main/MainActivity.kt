package com.example.raghu.dagger2testandroid.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.raghu.dagger2testandroid.R
import com.example.raghu.dagger2testandroid.models.User
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract

import javax.inject.Inject

import dagger.android.AndroidInjection


class MainActivity : AppCompatActivity(), MainPresenterContract.View {

    @Inject
    lateinit var mainPresenter: MainActivityPresenter

    private var textView: TextView? = null
    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.tv)
        button = findViewById<Button>(R.id.button)

        if (savedInstanceState != null) {
            button!!.isEnabled = false
            textView!!.text = savedInstanceState.getString("name")
        }

        button!!.setOnClickListener { mainPresenter!!.doSomething() }


    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter!!.unSubscribe()
    }

    override fun showData(user: User) {
        button!!.isEnabled = false
        textView!!.text = user.name
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", textView!!.text.toString())
    }
}
