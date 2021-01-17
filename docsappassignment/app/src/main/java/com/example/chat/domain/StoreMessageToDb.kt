package com.example.chat.domain

import com.example.chat.data.db.ChatMessage
import com.example.chat.data.db.NotSent
import com.example.chat.repository.ChatRepository
import javax.inject.Inject


class StoreMessageToDb @Inject constructor(private val chatRepository: ChatRepository) {


    suspend fun storeMessage(message: ChatMessage) = chatRepository.insertDb(message)


    suspend fun storeInNotSent(notSent: NotSent) = chatRepository.insertNotSent(notSent)


     suspend fun delete(id: Int) = chatRepository.delete(id)

}

