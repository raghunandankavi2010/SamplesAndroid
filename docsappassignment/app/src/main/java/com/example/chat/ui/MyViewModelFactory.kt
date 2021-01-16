package com.example.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.db.RoomSingleton

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val roomSingleton: RoomSingleton) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            ChatViewModel(roomSingleton) as T
        } else {
            ChatListViewModel(roomSingleton) as T
        }
    }
}