package com.example.chat.repository

import androidx.lifecycle.LiveData
import com.example.chat.db.ChatList
import com.example.chat.db.RoomSingleton

class ChatListRepository(val db: RoomSingleton) {

     fun getAllChatList(): LiveData<List<ChatList>> =  db.chatDao().getAllChats()

}