package com.example.chat.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.chat.data.db.ChatList
import com.example.chat.domain.GetChatLists


class ChatListViewModel @ViewModelInject constructor(private val getChatListUsecase: GetChatLists): ViewModel() {

    fun getAllChats(): LiveData<List<ChatList>> = getChatListUsecase.getData().asLiveData()


}