package com.example.raghu.dagger2testandroid.presenter;

import android.support.annotation.Nullable;

import com.example.raghu.dagger2testandroid.models.User;

/**
 * Created by raghu on 29/7/17.
 */

public interface MainPresenterContract {


    interface View {

        void showData(User user);


    }

    interface Presenter  {

        void doSomething();

        void unSubscribe();
    }
}

