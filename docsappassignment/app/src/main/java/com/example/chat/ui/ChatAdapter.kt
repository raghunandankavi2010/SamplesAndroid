package com.example.chat.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.R
import com.example.chat.data.db.ChatMessage

class ChatAdapter(private val context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mChatList: ArrayList<ChatMessage> = ArrayList()

    companion object {
        const val VIEW_TYPE_SEND= 1
        const val VIEW_TYPE_RECEIVE = 2
    }

    fun setChatList(chat: List<ChatMessage>) {
        if (mChatList.size==0) {
            mChatList.addAll(chat)
            notifyItemRangeInserted(0, mChatList.size)
        } else {
            updateListItems(chat)
        }
    }

    private fun updateListItems(chatList: List<ChatMessage>) {
        val diffCallback = ChatMessageDiff(this.mChatList, chatList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mChatList.clear()
        this.mChatList.addAll(chatList)
        diffResult.dispatchUpdatesTo(this)
    }

    private inner class SendViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.send)
        fun bind(position: Int) {
            val recyclerViewModel = mChatList[position]
            message.text = recyclerViewModel.chatMessage
        }
    }

    private inner class ReceiveViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.receive)
        fun bind(position: Int) {
            val recyclerViewModel = mChatList[position]
            message.text = recyclerViewModel.chatMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_SEND) {
            return SendViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chat_send, parent, false)
            )
        }
        return ReceiveViewHolder(
            LayoutInflater.from(context).inflate(R.layout.chat_receive, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mChatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mChatList[position].chatSendOrReceive == VIEW_TYPE_SEND) {
            (holder as SendViewHolder).bind(position)
        } else {
            (holder as ReceiveViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mChatList[position].chatSendOrReceive) {
            1 -> VIEW_TYPE_SEND
            else -> VIEW_TYPE_RECEIVE
        }

    }
}