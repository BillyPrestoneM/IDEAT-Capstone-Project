package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.HistoryRepository
import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(private val apiService: ApiService, private val tokenRepository: TokenRepository) : HistoryRepository {

    override suspend fun scanHistory(): Flow<ResultUtil<List<DataItem>>> {
        return flow {
            try {
                emit(ResultUtil.Loading)
                val token = tokenRepository.getToken()
                if (token == null) {
                    emit(ResultUtil.Error("Token not found"))
                    return@flow
                }
                val response = apiService.historyCall()
                println("Response status: ${response.message}")
                val data = response.data
                if (data.isNullOrEmpty()) {
                    emit(ResultUtil.Error("No data found"))
                } else {
                    emit(ResultUtil.Success(data))
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