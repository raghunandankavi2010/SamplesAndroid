package com.example.chat.model

import com.google.gson.annotations.SerializedName

data class Message (

	@SerializedName("chatBotName") val chatBotName : String,
	@SerializedName("chatBotID") val chatBotID : Int,
	@SerializedName("message") val message : String,
	@SerializedName("emotion") val emotion : String,
	var chatSendOrReceive: Int = 0
)