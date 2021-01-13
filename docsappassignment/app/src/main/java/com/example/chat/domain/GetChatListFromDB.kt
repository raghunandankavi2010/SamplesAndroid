package com.example.chat.domain

import androidx.lifecycle.LiveData
import com.example.chat.db.ChatMessage
import com.example.chat.db.RoomSingleton
import com.example.chat.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatListFromDB(val chatRepository: ChatRepository) {


    fun getAllChats(id: Int): Flow<List<ChatMessage>> =  chatRepository.getAllChats(id)
}