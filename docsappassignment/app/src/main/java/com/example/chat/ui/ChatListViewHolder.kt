package com.example.chat.ui

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.db.ChatList

class ChatListViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView){
    var title: TextView = itemView.findViewById(R.id.title)
    private val root: ConstraintLayout = itemView.findViewById<View>(R.id.root) as ConstraintLayout
    fun bindTo(item: ChatList, listener: (ChatList) -> Unit,) {
        title.text = item.chatListTitle
        root.setOnClickListener {
            listener(item)
        }
    }
}