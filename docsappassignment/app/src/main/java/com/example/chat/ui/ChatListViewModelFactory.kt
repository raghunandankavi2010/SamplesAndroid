package com.example.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.db.RoomSingleton

class ChatListViewModelFactory(private val roomSingleton: RoomSingleton) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatListViewModel(roomSingleton) as T
    }
}