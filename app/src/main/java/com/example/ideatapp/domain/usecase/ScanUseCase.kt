package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.ScanResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class ScanUseCase(private val scanRepository: ScanRepository) {
    suspend operator fun invoke (image: File): Flow<ResultUtil<ScanResponse>>{
        return scanRepository.scanFood(image)
    }
}