package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller


class TestActivity: AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mArrayList: ArrayList<DataModel>
    private var checkedItems: ArrayList<DataModel> = ArrayList()
    private lateinit var mAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initViews()
        loadList()

    }

    private fun initViews() {

        val button = findViewById<View>(R.id.button) as com.google.android.material.button.MaterialButton

        val nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }


        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView.setHasFixedSize(true)

        mRecyclerView.layoutManager = LinearLayoutManagerWithSmoothScroller(this)

        mAdapter = DataAdapter(checkListener = { checkedItem ->
            checkedItems.add(checkedItem)

        }, unCheckListener = { unCheckedItem ->
            checkedItems.add(unCheckedItem)
        }, listener = {
            Toast.makeText(this@TestActivity.applicationContext, "Clicked $it", Toast.LENGTH_LONG).show()
        })
        mRecyclerView.adapter = mAdapter

        button.setOnClickListener {
            mRecyclerView.smoothScrollToPosition(15)

        }
    }


    private fun loadList() {
        mArrayList = ArrayList()

        for(i in 0..20){
            val data = DataModel("Data Model $i")
            mArrayList.add(data)
        }
        mAdapter.add(mArrayList)
    }
}