package com.example.chat.mappers

import com.example.chat.model.ChatMessage
import com.example.chat.model.Message

class ChatMessageMapper: Mapper<Message, ChatMessage> {
    override fun map(input: Message): ChatMessage {
      return ChatMessage(input.chatBotName,input.chatBotID,input.chatBotName,input.message,input.chatSendOrReceive)
    }
}