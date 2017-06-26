package com.example.raghu.androidarchitecturecomponentstest

import android.arch.lifecycle.*
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LifecycleRegistryOwner {
    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry;
    }

    private var lifecycleRegistry = LifecycleRegistry(this)

    private lateinit var model : MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState!=null)
        {
            button.isEnabled= savedInstanceState.getBoolean("bool")
        }
        model  = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        button.setOnClickListener(object: View.OnClickListener{
            override fun onClick(b: View) {
               b.isEnabled =false
               model.startCounter()
            }

        })

        model.counterval.observe(this, object: Observer<String>
        {
            override fun onChanged(t: String?) {
                if(t.equals("finished"))
                {
                    button.isEnabled =true
                }
                textView.text= t
            }

        })

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean("bool", button.isEnabled);
    }
}
