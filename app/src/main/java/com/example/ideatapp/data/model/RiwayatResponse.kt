package com.example.ideatapp.data.model

import com.google.gson.annotations.SerializedName

data class RiwayatResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = emptyList(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("kalori")
	val kalori: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id_history")
	val idHistory: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("makanan")
	val makanan: String? = null
)
