package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryUseCase(private val historyRepository: HistoryRepository) {
    suspend operator fun invoke(): Flow<ResultUtil<List<DataItem>>> {
        return historyRepository.scanHistory()

    }
}