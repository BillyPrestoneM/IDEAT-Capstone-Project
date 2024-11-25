package com.example.ideatapp.login

import com.google.gson.annotations.SerializedName

data class LoginErrorResponse(

	@field:SerializedName("errors")
	val errors: Errors
)

data class Errors(

	@field:SerializedName("password")
	val password: List<List<String>>,

	@field:SerializedName("email")
	val email: List<List<String>>
)
