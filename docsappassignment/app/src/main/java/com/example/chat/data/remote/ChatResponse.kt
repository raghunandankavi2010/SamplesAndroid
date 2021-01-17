package com.example.chat.data.remote

import com.google.gson.annotations.SerializedName

data class ChatResponse (

		@SerializedName("success") val success : Int,
		@SerializedName("errorMessage") val errorMessage : String,
		@SerializedName("message") val message : Message,
		@SerializedName("data") val data : List<String>,

		)