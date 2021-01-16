package com.example.chat.mappers

import com.example.chat.model.ChatData
import com.example.chat.model.ChatResponse

class ChatDataMapper : Mapper<ChatResponse, ChatData> {
    override fun map(input: ChatResponse): ChatData {
        val chatMessageMapper = ChatMessageMapper()
        val chatMessage = chatMessageMapper.map(input.message)
        return ChatData(input.success, input.errorMessage, chatMessage, input.data)
    }
}