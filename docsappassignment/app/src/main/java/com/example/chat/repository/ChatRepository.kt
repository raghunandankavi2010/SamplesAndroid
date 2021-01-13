package com.example.chat.repository

import androidx.lifecycle.LiveData
import com.example.chat.API_KEY
import com.example.chat.CHAT_BOT_ID
import com.example.chat.EXTERNAL_ID
import com.example.chat.db.ChatMessage
import com.example.chat.db.NotSent
import com.example.chat.db.RoomSingleton
import com.example.chat.model.ChatResponse
import com.example.chat.network.RetrofitClient
import com.example.chat.util.Result
import com.example.chat.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class ChatRepository(val db: RoomSingleton) {

    suspend fun getChatMessage(message: String) = safeApiCall(
            call = { getChat(message) },
            errorMessage = "Error loading Chat data"
    )

    private suspend fun getChat(message: String): Result<ChatResponse> {
        val response = RetrofitClient.apiService.getChatResponse(API_KEY, message, CHAT_BOT_ID, EXTERNAL_ID)
        if (response.isSuccessful) {
            val chatResponse = response.body()
            if (chatResponse != null) {
                return Result.Success(data = chatResponse)
            }
        }
        return Result.Error(IOException("Error loading Chat Response ${response.code()} ${response.message()}"))
    }

    suspend fun insertDb(message: ChatMessage) {
        db.chatDao().insertAll(message)
    }

    fun getAllChats(id: Int): Flow<List<ChatMessage>> = db.chatDao().getAll(id)

    suspend fun insertNotSent(notSent: NotSent) = db.chatDao().insertNotSent(notSent)


     suspend fun delete(id: Int) =
        db.chatDao().deleteNotSent(id)


     fun getAllNotSent(id: Int): Flow<List<NotSent>> =
            db.chatDao().getAllNotSent(id)

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


