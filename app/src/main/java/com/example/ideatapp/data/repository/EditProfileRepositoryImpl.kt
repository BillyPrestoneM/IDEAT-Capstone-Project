package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.EditProfileResponse
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.EditProfileRepository
import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EditProfileRepositoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) :
    EditProfileRepository {

    override suspend fun editProfile(nama: String, jenisKelamin: String, tanggalLahir: String, beratBadan: Int, tinggiBadan: Int) : Flow<ResultUtil<List<EditProfileResponse>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val token = tokenRepository.getToken()
                if (token == null) {
                    emit(ResultUtil.Error("Tidak ada Token, Silahkan Login Kembali"))
                    return@flow
                }
                val response = apiService.editProfileCall(nama, jenisKelamin, tanggalLahir, beratBadan, tinggiBadan)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null) {
                        emit(ResultUtil.Error("Update Profile gagal"))
                    } else {
                        val mappedData = listOf(
                            EditProfileResponse(
                                message = data.message ?: "No message",
                                status = data.status ?: "Unknown"
                            )
                        )
                        emit(ResultUtil.Success(mappedData))
                    }
                } else {
                    emit(ResultUtil.Error("Profile request gagal"))
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