package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<ResultUtil<List<RegisterResponse>>>
    suspend fun register(
        nama: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<ResultUtil<List<RegisterResponse>>>

    suspend fun getToken(): String?
    suspend fun getName(): Flow<String?>
    suspend fun getEmail(): Flow<String?>
    suspend fun saveAuthToken(token: String, name: String, email: String)
    suspend fun clearSession()
}

