package com.example.raghu.dagger2testandroid.ui.main


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.raghu.dagger2testandroid.R
import com.example.raghu.dagger2testandroid.databinding.ActivityMainBinding
import com.example.raghu.dagger2testandroid.models.User
import com.example.raghu.dagger2testandroid.presenter.MainActivityPresenter
import com.example.raghu.dagger2testandroid.presenter.MainPresenterContract
import com.example.raghu.dagger2testandroid.ui.SetContentView
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainPresenterContract.View {

    @Inject
    lateinit var mainPresenter: MainActivityPresenter

    private val binding: ActivityMainBinding by SetContentView<AppCompatActivity,ActivityMainBinding>(R.layout.activity_main)

    /**
     *  private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        }
     */

    private var user:User? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)



        if (savedInstanceState != null) {
            this.user = user
            binding.user = savedInstanceState.getParcelable("user")
        }

        binding.button.setOnClickListener { mainPresenter.doSomething() }


    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.unSubscribe()
    }

    override fun showData(user: User) {
        this.user =user
         binding.user = user
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("user",binding.user)

    }
}
