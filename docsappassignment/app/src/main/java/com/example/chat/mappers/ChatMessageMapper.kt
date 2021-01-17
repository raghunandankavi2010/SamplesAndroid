package com.example.chat.mappers

import com.example.chat.data.model.ChatMessage
import com.example.chat.data.remote.Message
import javax.inject.Inject

class ChatMessageMapper @Inject constructor(): Mapper<Message, ChatMessage> {
    override fun map(input: Message): ChatMessage {
      return ChatMessage(input.chatBotName,input.chatBotID,input.chatBotName,input.message,input.chatSendOrReceive)
    }
}