package com.example.ideatapp.data.model

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("ScanData")
	val scanData: ScanData? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ScanData(

	@field:SerializedName("kalori")
	val kalori: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("makanan")
	val makanan: String? = null
)
