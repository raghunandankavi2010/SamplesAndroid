package raghu.me.myapplication.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import raghu.me.myapplication.R
import raghu.me.myapplication.databinding.ActivityListBinding
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.repo.Result

class ListScreen : AppCompatActivity(), ListAdapter.OnClickListener {

    private var mAdapter: ListAdapter? = null
    private val listScreenViewModel: ListScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityListBinding = DataBindingUtil.setContentView(this,R.layout.activity_list)
        binding.setLifecycleOwner(this)

        mAdapter = ListAdapter(this)

        binding.recyclerView?.let {
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

        binding.progressBar.visibility = View.VISIBLE
        listScreenViewModel.getUsers().observe(this,
            Observer<raghu.me.myapplication.repo.Result<List<Users>>> { t ->

                binding.progressBar.visibility = View.INVISIBLE
                binding.recyclerView.visibility = View.VISIBLE
                if(t is Result.Success){
                    mAdapter!!.setData(t.data)
                }

            })
    }

    override fun onClick(user: Users) {
        val intent = Intent(this, DetailScreen::class.java)
        intent.putExtra("user", user)
        startActivity(intent)

    }
}


