package com.example.chat.domain

import com.example.chat.db.ChatMessage
import com.example.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatListFromDB @Inject constructor(val chatRepository: ChatRepository) {


    fun getAllChats(id: Int): Flow<List<ChatMessage>> =  chatRepository.getAllChats(id)
}