package com.example.raghu.dagger2testandroid.presenter

import com.example.raghu.dagger2testandroid.models.User

/**
 * Created by raghu on 29/7/17.
 */

interface MainPresenterContract {


    interface View {

        fun showData(user: User)


    }

    interface Presenter {

        fun getData_coroutines()

        fun doSomething()

        fun unSubscribe()
    }
}

