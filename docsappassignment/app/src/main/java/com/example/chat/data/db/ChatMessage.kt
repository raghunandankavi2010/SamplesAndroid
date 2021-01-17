package com.example.chat.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessage(
    var chatMessage: String,
    var chatTime: Long,
    var chatListId: Int,
    var chatSendOrReceive: Int
){
    @PrimaryKey(autoGenerate = true)
    var chatId: Int = 0

}