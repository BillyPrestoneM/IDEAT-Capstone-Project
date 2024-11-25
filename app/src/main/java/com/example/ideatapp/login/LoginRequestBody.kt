package com.example.ideatapp.login

import com.google.gson.annotations.SerializedName

data class LoginRequestBody(

	@field:SerializedName("password ")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
