package com.example.chat.ui

import androidx.lifecycle.*
import com.example.chat.db.ChatList
import com.example.chat.db.RoomSingleton
import com.example.chat.domain.GetChatLists
import com.example.chat.repository.ChatListRepository


class ChatListViewModel(val db: RoomSingleton): ViewModel() {


    private val chatlistRespository = ChatListRepository(db)

    private val getChatListUsecase =  GetChatLists(chatlistRespository)


    fun getAllChats(): LiveData<List<ChatList>> = getChatListUsecase.getData().asLiveData()


}