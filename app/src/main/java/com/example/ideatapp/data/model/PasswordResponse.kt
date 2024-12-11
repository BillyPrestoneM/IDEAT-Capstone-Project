package com.example.ideatapp.data.model

import com.google.gson.annotations.SerializedName

data class PasswordResponse(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
