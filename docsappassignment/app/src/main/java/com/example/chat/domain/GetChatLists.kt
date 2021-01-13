package com.example.chat.domain

import com.example.chat.db.ChatList
import com.example.chat.repository.ChatListRepository
import kotlinx.coroutines.flow.Flow

class GetChatLists(private val chatListRepository: ChatListRepository) {


     fun getData(): Flow<List<ChatList>> = chatListRepository.getAllChatList()
}