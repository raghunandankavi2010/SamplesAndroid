package com.example.chat.repository

import com.example.chat.db.ChatList
import com.example.chat.db.RoomSingleton
import kotlinx.coroutines.flow.Flow

class ChatListRepository(val db: RoomSingleton) {

     fun getAllChatList(): Flow<List<ChatList>> =  db.chatDao().getAllChats()

}