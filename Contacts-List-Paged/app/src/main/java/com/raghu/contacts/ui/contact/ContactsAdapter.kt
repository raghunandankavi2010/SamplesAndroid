package com.raghu.contacts.ui.contact

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.raghu.contacts.R
import com.raghu.contacts.data.Contact


class ContactsAdapter(diffCallback: DiffUtil.ItemCallback<Contact>,private val itemClick: (Contact) -> Unit) :
        PagedListAdapter<Contact, ContactsAdapter.ContactViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent,
                        false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Contact?) {
            userName.text = item?.name
           // userPhone.text = item?.phoneNumber
            if(item?.photoUri!=null) {
                Glide.with(itemView.context)
                        .load(item.photoUri)
                        .placeholder(R.drawable.ic_launcher_background)
                        .transform(CropCircleTransformation())
                        .into(userPhoto)
            } else {

                Glide.with(itemView.context).clear(userPhoto)
                userPhoto.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_launcher_background))
            }

            itemView.setOnClickListener {
                if (item != null) {
                    itemClick(item)
                }
            }
        }

        private val userName: TextView = itemView.findViewById(R.id.user_name)
        private val userPhone: TextView = itemView.findViewById(R.id.user_number)
        private val userPhoto: ImageView = itemView.findViewById(R.id.user_photo)
    }

}

