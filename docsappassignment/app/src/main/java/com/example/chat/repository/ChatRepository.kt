package com.example.chat.repository

import com.example.chat.API_KEY
import com.example.chat.CHAT_BOT_ID
import com.example.chat.EXTERNAL_ID
import com.example.chat.db.ChatDao
import com.example.chat.db.ChatMessage
import com.example.chat.db.NotSent
import com.example.chat.db.RoomSingleton
import com.example.chat.mappers.ChatDataMapper
import com.example.chat.model.ChatData
import com.example.chat.model.ChatResponse
import com.example.chat.network.RetrofitClient
import com.example.chat.util.Result
import com.example.chat.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ChatRepository @Inject constructor(private val chatDao: ChatDao, private val chatDataMapper: ChatDataMapper) {

    suspend fun getChatMessage(message: String) = safeApiCall(
            call = { getChat(message) },
            errorMessage = "Error loading Chat data"
    )

    private suspend fun getChat(message: String): Result<ChatData> {
        val response = RetrofitClient.apiService.getChatResponse(API_KEY, message, CHAT_BOT_ID, EXTERNAL_ID)
        if (response.isSuccessful) {
            val chatResponse = response.body()
            if (chatResponse != null) {
                val chatData = chatDataMapper.map(chatResponse)
                return Result.Success(data = chatData)
            }else if(response.code()==401){
                return Result.Error(Exception("Authorization error"))
            }
        }
        return Result.Error(IOException("Error loading Chat Response ${response.code()} ${response.message()}"))
    }

    suspend fun insertDb(message: ChatMessage) {
       chatDao.insertAll(message)
    }

    fun getAllChats(id: Int): Flow<List<ChatMessage>> = chatDao.getAll(id)

    suspend fun insertNotSent(notSent: NotSent) = chatDao.insertNotSent(notSent)


     suspend fun delete(id: Int) =
       chatDao.deleteNotSent(id)


     fun getAllNotSent(id: Int): Flow<List<NotSent>> =
           chatDao.getAllNotSent(id)

    suspend fun sent(notSent: NotSent) = safeApiCall(
            call = { send(notSent.chatMessage) },
            errorMessage = "Error loading Chat data"
    )

    private suspend fun send(message: String): Result<ChatResponse> {
        val response = RetrofitClient.apiService.getChatResponse(API_KEY, message, CHAT_BOT_ID, EXTERNAL_ID)
        if (response.isSuccessful) {
            val chatResponse = response.body()
            if (chatResponse != null) {
                return Result.Success(data = chatResponse)
            }
        }
        return Result.Error(IOException("Error loading Chat Response ${response.code()} ${response.message()}"))
    }

}


