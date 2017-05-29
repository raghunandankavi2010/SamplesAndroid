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

import com.example.raghu.phonebook.entity.ContactEntity

import java.util.ArrayList

/**
 * Created by raghu on 20/5/17.
 */

class MainActivity : LifecycleActivity() {

    private var recyclerView: RecyclerView? = null
    private var mAdapter: PhoneBookAdapter? = null
    private val listcontacts = ArrayList<ContactEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpUiElements()
    }

    private fun setUpUiElements() {

        val toolbar = findViewById(R.id.toolbar) as Toolbar
       /* setSupportActionBar(toolbar)
        supportActionBar?.title = "Hellow"*/

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
        mAdapter = PhoneBookAdapter(this)
        val viewModel = ViewModelProviders.of(this).get(PhoneBookViewModel::class.java)

        subscribeUi(viewModel)

    }

    private fun subscribeUi(viewModel: PhoneBookViewModel) {
        // Update the list when the data changes
        viewModel.getContacts().observe(this,Observer<List<ContactEntity>>{
            contactEntities ->
                if(contactEntities!=null) {
                  mAdapter.setList(contactEntities)
                    Log.i(MainActivity.TAG, "Size : " + contactEntities.size)
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
