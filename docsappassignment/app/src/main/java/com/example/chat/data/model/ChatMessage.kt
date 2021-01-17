package com.example.chat.data.model


data class ChatMessage(
        val chatBotName: String,
        val chatBotID: Int,
        val message: String,
        val emotion: String,
        var chatSendOrReceive: Int = 0)