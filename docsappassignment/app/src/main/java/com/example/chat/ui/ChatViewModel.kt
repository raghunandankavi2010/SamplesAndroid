package com.example.chat.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.chat.data.db.ChatMessage
import com.example.chat.data.db.NotSent
import com.example.chat.domain.GetChatListFromDB
import com.example.chat.domain.GetChatUseCase
import com.example.chat.domain.StoreMessageToDb
import com.example.chat.data.remote.ChatResponse
import com.example.chat.util.AbsentLiveData
import com.example.chat.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChatViewModel @ViewModelInject constructor(private val getChatUseCase: GetChatUseCase, private val storeMessageToDb: StoreMessageToDb, private val getChatListFromDB: GetChatListFromDB,) : ViewModel() {


    private val _chatMessage = MutableLiveData<String?>()
    val chatMessage: LiveData<String?>
        get() = _chatMessage

    private val _chatNotSent = MutableLiveData<List<NotSent>?>()
    val chatNotSent: LiveData<List<NotSent>?>
        get() = _chatNotSent
    private val _chatSent= MutableLiveData<Result<ChatResponse>?>()
    val chatSent: LiveData<Result<ChatResponse>?>
        get() = _chatSent


    fun setChatMessage(chatMessage: String, id: Int) {
        _chatMessage.value = chatMessage
        val chatMess = ChatMessage(chatMessage, System.currentTimeMillis(), id, 1)
        store(chatMess)
    }

    val chatResponse = _chatMessage.switchMap { chatMessage ->
        if (chatMessage == null) {
            AbsentLiveData.create()
        } else {
            liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
                emit(getChatUseCase.getMessage(chatMessage))
            }
        }
    }

    fun getAllChats(id: Int): LiveData<List<ChatMessage>> = getChatListFromDB.getAllChats(id).asLiveData()

    fun store(chatMessage: ChatMessage) {

        viewModelScope.launch {
            storeMessageToDb.storeMessage(chatMessage)
        }

    }

    fun storeReceivedMessage(message: String, id: Int) {
        val chatMessage = ChatMessage(message, System.currentTimeMillis(), id, 2)
        store(chatMessage)
    }

    fun storeInNotSent(message: String, id: Int) {

        val notSent = NotSent(message, id,0)
        viewModelScope.launch {
           val inserted = storeMessageToDb.storeInNotSent(notSent)
            Log.i("inserted","$inserted")
        }
    }

    fun deleteNotSent(id: Int) {
        viewModelScope.launch() {
            val deleted =  storeMessageToDb.delete(id)
            Log.i("deleted","$deleted")
        }
    }

    fun storeInDb(chatMessage: String, id: Int) {
        val chatMess = ChatMessage(chatMessage, System.currentTimeMillis(), id, 1)
        store(chatMess)
    }

    fun getAllNotSent(id: Int) {
         _chatNotSent.value = getChatUseCase.getAll(id).asLiveData().value

    }

    fun sent(notSent: NotSent) {
      viewModelScope.launch(Dispatchers.IO) {
           _chatSent.postValue(getChatUseCase.send(notSent))

      }
    }
}