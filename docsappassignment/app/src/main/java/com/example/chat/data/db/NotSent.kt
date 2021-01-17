package com.example.chat.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotSent( var chatMessage: String,
                    var chatListId: Int,
                    var sent: Int
                    ) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}