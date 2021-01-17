package com.example.chat.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatMessage::class, ChatList::class, NotSent::class], version = 1, exportSchema = false)
abstract class RoomSingleton : RoomDatabase() {
    abstract fun chatDao(): ChatDao

}