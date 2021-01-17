package com.example.chat.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.chat.R
import com.example.chat.data.db.ChatList


 class ChatListAdapter(private val context: Context, private val listener: (ChatList) -> Unit) : ListAdapter<ChatList?,ChatListViewHolder?>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it,listener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {

        return ChatListViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_list_row, parent, false))
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ChatList?> = object : DiffUtil.ItemCallback<ChatList?>() {
            override fun areItemsTheSame(
                 oldUser: ChatList, newUser: ChatList
            ): Boolean {
                // User properties may have changed if reloaded from the DB, but ID is fixed
                return oldUser.chatListId == newUser.chatListId
            }

            override fun areContentsTheSame(
                oldUser: ChatList,  newUser: ChatList
            ): Boolean {
                // NOTE: if you use equals, your object must properly override Object#equals()
                // Incorrectly returning false here will result in too many animations.
                return oldUser == newUser
            }
        }
    }

}