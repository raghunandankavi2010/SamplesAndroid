package com.example.raghu.dagger2testandroid.ui.main


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

    private val binding: ActivityMainBinding by SetContentView(R.layout.activity_main)

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
            binding.user = savedInstanceState.getParcelable("user")
        }

        binding.button.setOnClickListener(onClickListener)
        binding.button.setOnClickListener {
           // mainPresenter.doSomething()
            //mainPresenter.getData()
            mainPresenter.getData_with_coroutines_retrofit()
        }


    }

    val onClickListener = object: View.OnClickListener{
        override fun onClick(p0: View?) {

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //mainPresenter.unSubscribe()
    }

    override fun showData(user: User?) {
        binding.user = user
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("user",binding.user)

    }
}