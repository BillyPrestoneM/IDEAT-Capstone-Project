package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.DataProfileResponse
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.ProfileRepository
import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val apiService: ApiService,
    private val tokenRepository: TokenRepository
) : ProfileRepository {

    override suspend fun dataProfile(
        nama: String,
        email: String,
        jenisKelamin: String,
        tanggalLahir: String,
        beratBadan: Int,
        tinggiBadan: Int
    ): Flow<ResultUtil<List<DataProfileResponse>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val response = apiService.profileCall(
                    nama,
                    email,
                    jenisKelamin,
                    tanggalLahir,
                    beratBadan,
                    tinggiBadan
                )

                if (response.isSuccessful) {
                    val data = response.body()
                    if (data == null || data.data == null) {
                        emit(ResultUtil.Error("Profile Failed"))
                    } else {
                        val mappedData = listOf(
                            DataProfileResponse(
                                data = data.data,
                                message = data.message ?: "No message",
                                status = data.status ?: "Unknown"
                            )
                        )
                        emit(ResultUtil.Success(mappedData))
                    }
                } else {
                    emit(ResultUtil.Error("Profile request failed"))
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
