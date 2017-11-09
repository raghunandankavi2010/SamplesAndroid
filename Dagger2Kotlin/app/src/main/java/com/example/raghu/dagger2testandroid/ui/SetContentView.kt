package com.example.raghu.dagger2testandroid.ui


import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KProperty

/**
 * Created by raghu on 9/11/17.
 */
class SetContentView<in R: AppCompatActivity,out T: ViewDataBinding>(@LayoutRes private val layoutRes:Int) {
    private var value: T? =null
    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>) :T {

        if(value==null){
            value = DataBindingUtil.setContentView(thisRef,layoutRes)
        }
        return value!!
    }
}