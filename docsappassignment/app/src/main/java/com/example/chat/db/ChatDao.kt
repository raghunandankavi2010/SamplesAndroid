package com.example.chat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface ChatDao {

    @Query("SELECT * FROM chatmessage WHERE chatListId LIKE :id")
    fun getAllChatMessages(id: Int): Flow<List<ChatMessage>>

    fun getAll(id: Int) = getAllChatMessages(id).distinctUntilChanged()

    @Insert
    suspend fun insertAll(chatMessage: ChatMessage)

    @Delete
    fun delete(chatMessage: ChatMessage)

    @Insert
    fun insertChatList(chatList: List<ChatList>)

    @Query("SELECT * FROM chatlist")
    fun getChats(): Flow<List<ChatList>>


    fun getAllChats() = getChats().distinctUntilChanged()

    @Insert
    suspend fun insertNotSent(notSent: NotSent): Long

    @Query("DELETE FROM notsent WHERE chatListId LIKE :id")
    suspend fun deleteNotSent(id: Int): Int

    @Query("SELECT * FROM notsent WHERE chatListId LIKE :id ")
    fun getNotSent(id: Int): Flow<List<NotSent>>

    fun getAllNotSent(id: Int) = getNotSent(id).distinctUntilChanged()



}