package com.example.chat.domain


import androidx.lifecycle.LiveData
import com.example.chat.db.NotSent
import com.example.chat.model.ChatResponse
import com.example.chat.util.Result
import com.example.chat.repository.ChatRepository

class GetChatUseCase(private val chatRepository: ChatRepository) {

    suspend fun getMessage(message: String): Result<ChatResponse> = chatRepository.getChatMessage(message)


    suspend fun getAll(id: Int): List<NotSent>  =  chatRepository.getAllNotSent(id)

    suspend fun send(notSent: NotSent) : Result<ChatResponse>  = chatRepository.sent(notSent)

}