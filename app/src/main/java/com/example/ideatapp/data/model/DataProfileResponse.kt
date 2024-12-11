package com.example.ideatapp.data.model

import com.google.gson.annotations.SerializedName

data class DataProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataProfile(

	@field:SerializedName("beratBadan")
	val beratBadan: Int? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jenisKelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggalLahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("tinggiBadan")
	val tinggiBadan: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
