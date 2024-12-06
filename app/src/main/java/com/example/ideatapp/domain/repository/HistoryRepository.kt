package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun scanHistory(): Flow<ResultUtil<List<DataItem>>>
    suspend fun getToken(): String?
}