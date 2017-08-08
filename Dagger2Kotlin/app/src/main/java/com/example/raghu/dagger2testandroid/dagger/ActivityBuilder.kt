package com.example.raghu.dagger2testandroid.dagger

import com.example.raghu.dagger2testandroid.ui.main.MainActivity
import com.example.raghu.dagger2testandroid.ui.main.MainActivityModule

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by raghu on 4/8/17.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun bindMainActivity(): MainActivity


}