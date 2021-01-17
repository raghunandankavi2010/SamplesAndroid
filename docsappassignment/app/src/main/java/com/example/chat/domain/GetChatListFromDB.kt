package com.example.chat.domain

import com.example.chat.data.db.ChatMessage
import com.example.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatListFromDB @Inject constructor(private val chatRepository: ChatRepository) {


    fun getAllChats(id: Int): Flow<List<ChatMessage>> =  chatRepository.getAllChats(id)
}