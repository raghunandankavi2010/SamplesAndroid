package com.example.chat.repository

import com.example.chat.db.ChatDao
import com.example.chat.db.ChatList
import com.example.chat.db.RoomSingleton
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatListRepository @Inject constructor(val chatDao: ChatDao) {

     fun getAllChatList(): Flow<List<ChatList>> =  chatDao.getAllChats()

}