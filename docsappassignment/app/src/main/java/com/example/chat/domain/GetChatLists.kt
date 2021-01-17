package com.example.chat.domain

import com.example.chat.data.db.ChatList
import com.example.chat.repository.ChatListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatLists @Inject constructor(private val chatListRepository: ChatListRepository) {


     fun getData(): Flow<List<ChatList>> = chatListRepository.getAllChatList()
}