package com.example.chat.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.chat.data.db.ChatMessage

class ChatMessageDiff(
    private val moldChatMessageList: List<ChatMessage>,
    private val mnewChatMessageList: List<ChatMessage>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return moldChatMessageList.size
    }

    override fun getNewListSize(): Int {
        return mnewChatMessageList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return moldChatMessageList[oldItemPosition].chatId == mnewChatMessageList[newItemPosition].chatId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val chatMessage = moldChatMessageList[oldItemPosition].chatMessage
        val chatMessage1 = mnewChatMessageList[newItemPosition].chatMessage
        return chatMessage == chatMessage1
    }

}