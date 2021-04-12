package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTestBinding


class TestActivity : AppCompatActivity() {


    private lateinit var mArrayList: ArrayList<DataModel>
    private var checkedItems: ArrayList<DataModel> = ArrayList()
    private lateinit var mAdapter: DataAdapter
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()
        loadList()

    }

    private fun initViews() {

        binding.recyclerView.setHasFixedSize(true)

        mAdapter = DataAdapter(this@TestActivity,checkListener = { checkedItem ->
            checkedItems.add(checkedItem)

        }, unCheckListener = { unCheckedItem ->
            checkedItems.add(unCheckedItem)
        }, listener = {
            Toast.makeText(this@TestActivity.applicationContext, "Clicked $it", Toast.LENGTH_LONG).show()
        })

        binding.recyclerView.apply{
            layoutManager = LinearLayoutManagerWithSmoothScroller(this@TestActivity)
            adapter = mAdapter
            setHasFixedSize(true)
        }

        binding.button.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(15)
        }
    }

    private fun loadList() {
        mArrayList = ArrayList()

        for (i in 0..20) {
            val data = DataModel("Data Model $i")
            mArrayList.add(data)
        }
        mAdapter.add(mArrayList)
    }
}