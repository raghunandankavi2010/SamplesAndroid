package com.example.chat.domain


import com.example.chat.db.NotSent
import com.example.chat.model.ChatResponse
import com.example.chat.repository.ChatRepository
import com.example.chat.util.Result
import kotlinx.coroutines.flow.Flow

class GetChatUseCase(private val chatRepository: ChatRepository) {

    suspend fun getMessage(message: String): Result<ChatResponse> = chatRepository.getChatMessage(message)


    fun getAll(id: Int): Flow<List<NotSent>> =  chatRepository.getAllNotSent(id)

    suspend fun send(notSent: NotSent) : Result<ChatResponse>  = chatRepository.sent(notSent)

}