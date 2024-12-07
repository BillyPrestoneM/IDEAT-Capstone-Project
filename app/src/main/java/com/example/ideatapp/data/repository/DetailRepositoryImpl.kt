package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.DetailResponse
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.DetailRepository
import com.example.ideatapp.domain.repository.TokenRepository

class DetailRepositoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) : DetailRepository {
    override suspend fun detailHistory(idHistory: String): ResultUtil<DetailResponse> {
        return try {
            val token = tokenRepository.getToken()
            if (token==null) {
                return ResultUtil.Error("Token not found")
            }

            val response = apiService.detailCall(idHistory)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ResultUtil.Success(body)
                } else {
                    ResultUtil.Error("Response body is null")
                }
            } else {
                ResultUtil.Error("Request failed with code: ${response.code()}")
            }

        }catch (e: Exception) {
            ResultUtil.Error("Request failed: ${e.message}")

        }
    }
}