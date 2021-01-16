package com.example.chat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.chat.db.ChatList
import com.example.chat.db.RoomSingleton
import com.example.chat.domain.GetChatLists
import com.example.chat.domain.GetChatUseCase
import com.example.chat.repository.ChatListRepository


class ChatListViewModel @ViewModelInject constructor(val db: RoomSingleton, val getChatListUsecase: GetChatLists): ViewModel() {

    fun getAllChats(): LiveData<List<ChatList>> = getChatListUsecase.getData().asLiveData()


}