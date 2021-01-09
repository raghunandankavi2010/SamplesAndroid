package com.example.chat.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChatDao {

    @Query("SELECT * FROM chatmessage WHERE chatListId LIKE :id")
    fun getAll(id: Int): LiveData<List<ChatMessage>>

    @Insert
    suspend fun insertAll(chatMessage: ChatMessage)

    @Delete
    fun delete(chatMessage: ChatMessage)

    @Insert
    fun insertChatList(chatList: List<ChatList>)

    @Query("SELECT * FROM chatlist")
    fun getAllChats(): LiveData<List<ChatList>>

    @Insert
    suspend fun insertNotSent(notSent: NotSent): Long

    @Query("DELETE FROM notsent WHERE chatListId LIKE :id")
    suspend fun deleteNotSent(id: Int): Int

    @Query("SELECT * FROM notsent WHERE chatListId LIKE :id ")
    suspend  fun getAllNotSent(id: Int): List<NotSent>

}