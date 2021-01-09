package com.example.chat.domain

import androidx.lifecycle.LiveData
import com.example.chat.db.ChatMessage
import com.example.chat.db.RoomSingleton
import com.example.chat.repository.ChatRepository

class GetChatListFromDB(val chatRepository: ChatRepository) {


    fun getAllChats(id: Int): LiveData<List<ChatMessage>> =  chatRepository.getAllChats(id)
}