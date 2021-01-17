package com.example.chat.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatList(
        var chatListId: Int,
        var chatListTitle: String
){
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0

}
