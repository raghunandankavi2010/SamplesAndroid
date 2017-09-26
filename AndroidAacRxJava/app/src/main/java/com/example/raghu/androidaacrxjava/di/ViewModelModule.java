package com.example.raghu.androidaacrxjava.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;


import com.example.raghu.androidaacrxjava.ApiViewModel;
import com.example.raghu.androidaacrxjava.viewmodel.DemoViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(ApiViewModel.class)
    abstract ViewModel bindRepoViewModel(ApiViewModel apiViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DemoViewModelFactory factory);
}
