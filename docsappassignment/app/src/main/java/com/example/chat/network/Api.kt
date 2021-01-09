package com.example.chat.network

import com.example.chat.model.ChatResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("api/chat/")
    suspend fun getChatResponse(
        @Query("apiKey") page: String,
        @Query("message") mess: String,
        @Query("chatBotID") botId: String,
        @Query("externalID") externalId: String,
    ): Response<ChatResponse>

}