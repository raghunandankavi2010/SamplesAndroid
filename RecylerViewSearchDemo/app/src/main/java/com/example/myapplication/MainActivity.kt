package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(){
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mArrayList: ArrayList<DataModel>
    private var checkedItems: ArrayList<DataModel> = ArrayList()
    private lateinit var mAdapter: DataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        initViews()
        loadList()
    }

    private fun initViews() {
        mRecyclerView = findViewById<View>(R.id.card_recycler_view) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = layoutManager
        mAdapter = DataAdapter( checkListener =  { checkedItem ->
            checkedItems.add(checkedItem)

        },unCheckListener = { unCheckedItem ->
            checkedItems.add(unCheckedItem)
        },listener = {
            Toast.makeText(this@MainActivity.applicationContext, "Clicked $it", Toast.LENGTH_LONG).show()
        })
        mRecyclerView!!.adapter = mAdapter
    }


    private fun loadList() {
        mArrayList = ArrayList()
        val data1 = DataModel("Cupcake")
        mArrayList.add(data1)
        val data2 = DataModel("Donut")
        mArrayList.add(data2)
        val data3 = DataModel("Eclair")
        mArrayList.add(data3)
        val data4 = DataModel("Froyo")
        mArrayList.add(data4)
        val data5 = DataModel("Gingerbread")
        mArrayList.add(data5)
        val data6 = DataModel("Honeycomb")
        mArrayList.add(data6)
        val data7 = DataModel("Ice Cream Sandwich")
        mArrayList.add(data7)
        val data8 = DataModel("Ice JellyBean Sandwich")
        mArrayList.add(data8)
        val data9 = DataModel("Kitkat")
        mArrayList.add(data9)
        val data10 = DataModel("Lollipop")
        mArrayList.add(data10)
        val data11 = DataModel("MarshMallow")
        mArrayList.add(data11)
        val data12 = DataModel("Nougat")
        mArrayList.add(data12)
        val data13 = DataModel("Oreo")
        mArrayList.add(data13)
        val data14 = DataModel("Pie")
        mArrayList.add(data14)
        val data15 = DataModel("Android 10")
        mArrayList.add(data15)
        val data16 = DataModel("Android 11")
        mArrayList.add(data16)

        mAdapter.add(mArrayList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        search(searchView)

        val checkBox = menu.findItem(R.id.menuShow).actionView as CheckBox
        //checkBox.setButtonDrawable(R.drawable.star) //set the icon to star.xml


        checkBox.setOnCheckedChangeListener { _, isChecked ->
            //set the action of the checkbox
            if(isChecked){
                mAdapter.selectAllItems()
            } else {
                mAdapter.unSelectAllItems()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter.filter.filter(newText)
                return true
            }
        })
    }

}