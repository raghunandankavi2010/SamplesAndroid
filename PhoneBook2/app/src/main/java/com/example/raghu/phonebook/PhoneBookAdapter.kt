package com.example.raghu.phonebook

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.example.raghu.phonebook.entity.ContactEntity
import java.util.ArrayList
import android.widget.TextView
import android.view.View;
import com.example.raghu.phonebook.PhoneBookAdapter.MyViewHolder
import android.view.LayoutInflater





/**
 * Created by raghu on 20/5/17.
 */
class PhoneBookAdapter(context: MainActivity) : RecyclerView.Adapter<PhoneBookAdapter.MyViewHolder>() {

    private val mContext = context
    private val listcontacts: MutableList<ContactEntity> =  mutableListOf<ContactEntity>()
    //private  lateinit var listcontacts? : M
    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): PhoneBookAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.card_view, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listcontacts.size
    }

    override fun onBindViewHolder(holder: PhoneBookAdapter.MyViewHolder?, index: Int) {

        holder?.name?.text= listcontacts.get(index).firstName
        holder?.number?.text = listcontacts.get(index).number
        holder?.nickname?.text= listcontacts.get(index).nickName
        val generator = ColorGenerator.MATERIAL // or use DEFAULT
        // generate random color
        val color = generator.getColor(listcontacts.get(index).firstName)
        holder?.photo?.setUser(listcontacts.get(index), color)
    }

    fun setList(list:List<ContactEntity>)
    {
        listcontacts.addAll(list)
        notifyDataSetChanged()
    }


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView
        var number: TextView
        var nickname: TextView
        var photo: AvatarImageView

        init {
            name = view.findViewById(R.id.user_name) as TextView
            number = view.findViewById(R.id.user_number) as TextView
            nickname = view.findViewById(R.id.total_duration) as TextView
            photo = view.findViewById(R.id.user_photo) as AvatarImageView

        }
    }


}