package com.example.chat.model


data class ChatData(
        val success: Int,
        val errorMessage: String,
        val message: ChatMessage,
        val data: List<String>,

        )
