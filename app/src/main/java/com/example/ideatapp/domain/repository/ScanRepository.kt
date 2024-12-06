package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.ScanResponse
import com.example.ideatapp.di.utils.ResultUtil
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ScanRepository {
    suspend fun scanFood(file: File): Flow<ResultUtil<ScanResponse>>
}