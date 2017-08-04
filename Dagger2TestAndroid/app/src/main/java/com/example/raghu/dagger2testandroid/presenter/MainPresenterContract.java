package com.example.raghu.dagger2testandroid.presenter;

import android.support.annotation.Nullable;

/**
 * Created by raghu on 29/7/17.
 */

public interface MainPresenterContract {


    interface View {

        void showData(String message);




    }

    interface Presenter  {

        void doSomething();
    }
}

