package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.PasswordResponse
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.PasswordRepository
import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PasswordRepositoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) : PasswordRepository {

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ): Flow<ResultUtil<List<PasswordResponse>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val token = tokenRepository.getToken()
                if (token == null) {
                    emit(ResultUtil.Error("Tidak ada Token, Silahkan Login Kembali"))
                    return@flow
                }
                val response = apiService.changePasswordCall(oldPassword, newPassword, confirmNewPassword)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        emit(ResultUtil.Error("Update Password gagal"))
                    } else {
                        tokenRepository.clearSession()
                        val mappedData = listOf(
                            PasswordResponse(
                                message = data.message ?: "No message",
                                status = data.status ?: "Unknown"
                            )
                        )
                        emit(ResultUtil.Success(mappedData))
                    }
                } else {
                    emit(ResultUtil.Error("Password request gagal"))
                }
            } catch (e: Exception) {
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }

    override suspend fun getToken(): String? {
        return tokenRepository.getToken()
    }
}