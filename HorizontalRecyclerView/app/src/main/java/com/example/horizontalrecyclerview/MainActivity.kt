package com.example.horizontalrecyclerview

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    // Array list for recycler view data source
    var source: ArrayList<String>? = null

    // Layout Manager
    var RecyclerViewLayoutManager: RecyclerView.LayoutManager? = null

    // adapter class object
    var mAdapter: MyAdapter? = null

    // Linear Layout Manager
    var HorizontalLayout: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.rv)
        RecyclerViewLayoutManager = LinearLayoutManager(this);

        // Set LayoutManager on Recycler View


        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList()

        // calling constructor of adapter
        // with source list as a parameter
        mAdapter = MyAdapter(source!!)

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        val radius = resources.getDimensionPixelSize(R.dimen.radius)
        val dotsHeight = resources.getDimensionPixelSize(R.dimen.dots_height)
        val color = Color.RED

        PagerSnapHelper().attachToRecyclerView(recyclerView)
        recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = HorizontalLayout
            adapter = mAdapter
            addItemDecoration(
                DotsIndicatorDecoration(
                    radius,
                    radius * 4,
                    dotsHeight,
                    color,
                    color
                )
            )
        }
    }

    // Function to add items in RecyclerView.
    fun AddItemsToRecyclerViewArrayList() {
        // Adding items to ArrayList
        source = ArrayList<String>()
        source?.let {
            it.add("gfg");
            it.add("is");
            it.add("best");
            it.add("site");
            it.add("for");
            it.add("interview");
            it.add("preparation");
        }

    }
}