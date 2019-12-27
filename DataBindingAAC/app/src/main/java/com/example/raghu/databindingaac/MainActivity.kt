package com.example.raghu.databindingaac

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.raghu.databindingaac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val model = ViewModelProviders.of(this).get(MyViewModel::class.java)
        binding.viewModel = model
        binding.button.setOnClickListener { model.check() }
        val watcher: TextWatcherAdapter = object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable) {
                model.check()
            }
        }
        //binding.editInput.addTextChangedListener(watcher);
// binding.editInput2.addTextChangedListener(watcher);
    }
}