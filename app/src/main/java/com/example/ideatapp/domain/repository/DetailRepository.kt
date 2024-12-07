package com.example.ideatapp.domain.repository

import com.example.ideatapp.data.model.DetailResponse
import com.example.ideatapp.di.utils.ResultUtil

interface DetailRepository {
    suspend fun detailHistory(idHistory: String): ResultUtil<DetailResponse>
}