package com.example.raghu.phonebook

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.example.raghu.phonebook.entity.ContactEntity
import java.util.ArrayList

/**
 * Created by raghu on 20/5/17.
 */
class PhoneBookAdapter(context: MainActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val listcontacts: MutableList<ContactEntity> =  mutableListOf<ContactEntity>()
    //private  lateinit var listcontacts? : M
    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return listcontacts.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setList(list:List<ContactEntity>)
    {
        listcontacts.addAll(list)
        notifyDataSetChanged()
    }


}