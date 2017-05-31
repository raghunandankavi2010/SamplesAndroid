package com.example.raghu.phonebook

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar

import com.example.raghu.phonebook.entity.ContactEntity

import java.util.ArrayList

/**
 * Created by raghu on 20/5/17.
 */

class MainActivity : LifecycleActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: PhoneBookAdapter
    private val listcontacts = ArrayList<ContactEntity>()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUiElements()
    }

    private fun setUpUiElements() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
       /* setSupportActionBar(toolbar)
        supportActionBar?.title = "Hellow"*/

        progressBar = findViewById(R.id.progressBar) as ProgressBar

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
        mAdapter = PhoneBookAdapter(this)
        recyclerView?.adapter = mAdapter
        val viewModel = ViewModelProviders.of(this).get(PhoneBookViewModel::class.java)

        subscribeUi(viewModel)

    }

    private fun subscribeUi(viewModel: PhoneBookViewModel) {
        // Update the list when the data changes
        viewModel.getContacts().observe(this,Observer<List<ContactEntity>>{
            contactEntities ->
                if(contactEntities!=null) {
                    progressBar.visibility = View.GONE
                  mAdapter?.setList(contactEntities)
                    Log.i(MainActivity.TAG, "Size : " + contactEntities.size)
                }else {
                    progressBar.visibility = View.VISIBLE
                }

        })

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // action with ID action_refresh was selected
            R.id.action_refresh -> {
            }

            else -> {
            }
        }

        return true
    }

    companion object {
        private val TAG = "MainActivity"
    }

}
