package com.example.chat.domain

import com.example.chat.db.ChatMessage
import com.example.chat.db.NotSent
import com.example.chat.repository.ChatRepository


class StoreMessageToDb(private val chatRepository: ChatRepository) {


    suspend fun storeMessage(message: ChatMessage){
        chatRepository.insertDb(message)

    }

    suspend fun storeInNotSent(notSent: NotSent) = chatRepository.insertNotSent(notSent)


     suspend fun delete(id: Int) =
        chatRepository.delete(id)

}

