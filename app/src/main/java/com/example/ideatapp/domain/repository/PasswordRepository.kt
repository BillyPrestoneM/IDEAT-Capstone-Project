package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.PasswordResponse
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmNewPassword: String): Flow<ResultUtil<List<PasswordResponse>>>
    suspend fun getToken(): String?
}