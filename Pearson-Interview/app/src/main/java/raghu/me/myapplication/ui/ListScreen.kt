package raghu.me.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import raghu.me.myapplication.R
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.network.Api
import raghu.me.myapplication.di.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ListScreen : AppCompatActivity(), ListAdapter.OnClickListener {

    private var progressbar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: ListAdapter? = null
    private val listScreenViewModel: ListScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        progressbar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        mAdapter = ListAdapter(this)
        recyclerView?.let {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = mAdapter
            val dividerItemDecoration = DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
            ContextCompat.getDrawable(this, R.drawable.divider)?.let { it1 -> dividerItemDecoration.setDrawable(it1) }
            it.addItemDecoration(dividerItemDecoration)
        }
        progressbar!!.visibility = View.VISIBLE
        listScreenViewModel.getUsers().observe(this,
            Observer<List<Users>> { t ->
                progressbar!!.visibility = View.INVISIBLE
                recyclerView!!.visibility = View.VISIBLE
                mAdapter!!.setData(t)
            })


        /*val service = retrofitDependency.provideRetrofit().create(Api::class.java)
        service.users.enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.isSuccessful) {
                    progressbar!!.visibility = View.GONE
                    recyclerView!!.visibility = View.VISIBLE
                    val list = response.body()
                    list?.let { mAdapter!!.setData(it) }
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                progressbar!!.visibility = View.GONE
                t.printStackTrace()
            }
        })*/
    }

    override fun onClick(user: Users) {
        val intent = Intent(this, DetailScreen::class.java)
        intent.putExtra("user", user)
        startActivity(intent)

    }
}


