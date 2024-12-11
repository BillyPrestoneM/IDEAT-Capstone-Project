package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.EditProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface EditProfileRepository {
    suspend fun editProfile(nama: String, jenisKelamin: String, tanggalLahir: String, beratBadan: Int, tinggiBadan: Int): Flow<ResultUtil<List<EditProfileResponse>>>
    suspend fun getToken(): String?
}