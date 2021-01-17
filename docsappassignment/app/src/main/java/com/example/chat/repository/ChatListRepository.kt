package com.example.chat.repository

import com.example.chat.data.db.ChatDao
import com.example.chat.data.db.ChatList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatListRepository @Inject constructor(private val chatDao: ChatDao) {

     fun getAllChatList(): Flow<List<ChatList>> =  chatDao.getAllChats()

}