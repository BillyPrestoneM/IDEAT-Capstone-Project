package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.DataProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun dataProfile(nama: String, email: String, jenisKelamin: String, tanggalLahir: String, beratBadan: Int, tinggiBadan: Int): Flow<ResultUtil<List<DataProfileResponse>>>
    suspend fun getToken(): String?

}