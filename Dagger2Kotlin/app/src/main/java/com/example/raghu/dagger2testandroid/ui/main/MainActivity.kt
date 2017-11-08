package com.example.raghu.dagger2testandroid.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.raghu.dagger2testandroid.R
import com.example.raghu.dagger2testandroid.R.id.button
import com.example.raghu.dagger2testandroid.databinding.ActivityMainBinding
import com.example.raghu.dagger2testandroid.models.User
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract

import javax.inject.Inject

import dagger.android.AndroidInjection


class MainActivity : AppCompatActivity(), MainPresenterContract.View {

    @Inject
    lateinit var mainPresenter: MainActivityPresenter
    lateinit  var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        if (savedInstanceState != null) {
            binding.button.isEnabled = false
            binding.tv.text = savedInstanceState.getString("name")
        }

        binding.button.setOnClickListener { mainPresenter.doSomething() }


    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.unSubscribe()
    }

    override fun showData(user: User) {
         binding.user = user
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("name", binding.tv.text.toString())
    }
}
