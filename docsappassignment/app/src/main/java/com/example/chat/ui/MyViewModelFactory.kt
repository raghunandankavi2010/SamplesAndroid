package com.example.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat.db.RoomSingleton

class MyViewModelFactory(private val roomSingleton: RoomSingleton) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(roomSingleton) as T
    }
}