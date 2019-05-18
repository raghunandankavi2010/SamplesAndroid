package raghu.me.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.github.binding.FragmentDataBindingComponent
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import raghu.me.myapplication.AppExecutors
import raghu.me.myapplication.R
import raghu.me.myapplication.databinding.ActivityListBinding
import raghu.me.myapplication.models.Users
import raghu.me.myapplication.repo.Result

class ListScreen : AppCompatActivity() {

    private var mAdapter: ListAdapter? = null
    private val listScreenViewModel: ListScreenViewModel by viewModel()
    private val appExecutors: AppExecutors by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityListBinding = DataBindingUtil.setContentView(this,R.layout.activity_list)
        binding.setLifecycleOwner(this)

        listScreenViewModel.getUsers()

        var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
        //val adapter = ContributorAdapter(dataBindingComponent, appExecutors)
        mAdapter = ListAdapter(dataBindingComponent,appExecutors,this){
            user -> val intent = Intent(this, DetailScreen::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.recyclerView.let {
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

        val liveData: LiveData<Result<List<Users>>> = listScreenViewModel.getUsers()
        binding.progressVisibility = true

        liveData.observe(this,
                Observer<raghu.me.myapplication.repo.Result<List<Users>>> { t ->

                    //binding.progressBar.visibility = View.INVISIBLE
                   // binding.recyclerView.visibility = View.VISIBLE
                    if(t!=null && t is Result.Success){
                        binding.progressVisibility = false
                        mAdapter!!.submitList(t.data)
                    } else {
                        mAdapter!!.submitList(emptyList())
                    }

                })
    }

}


