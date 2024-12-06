package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.DetailRiwayatResponse
import com.example.ideatapp.di.utils.ResultUtil

interface DetailRepository {
    suspend fun detailHistory(idHistory: String): ResultUtil<DetailRiwayatResponse>
}