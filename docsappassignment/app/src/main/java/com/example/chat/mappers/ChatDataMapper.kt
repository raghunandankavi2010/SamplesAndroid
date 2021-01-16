package com.example.chat.mappers

import com.example.chat.model.ChatData
import com.example.chat.model.ChatResponse
import javax.inject.Inject

class ChatDataMapper  @Inject constructor(val chatMessageMapper: ChatMessageMapper): Mapper<ChatResponse, ChatData> {
    override fun map(input: ChatResponse): ChatData {

        val chatMessage = chatMessageMapper.map(input.message)
        return ChatData(input.success, input.errorMessage, chatMessage, input.data)
    }
}