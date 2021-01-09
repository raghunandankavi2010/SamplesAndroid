package com.example.chat.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatList(
        var chatListId: Int,
        var chatListTitle: String
){
        @PrimaryKey(autoGenerate = true)
        var Id: Int = 0

}
