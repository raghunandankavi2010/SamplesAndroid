package com.example.chat.domain

import androidx.lifecycle.LiveData
import com.example.chat.db.ChatList
import com.example.chat.repository.ChatListRepository

class GetChatLists(private val chatListRepository: ChatListRepository) {


     fun getData(): LiveData<List<ChatList>> = chatListRepository.getAllChatList()
}