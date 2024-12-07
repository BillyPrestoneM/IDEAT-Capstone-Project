package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.DetailResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.DetailRepository

class DetailUseCase(private val detailRepository: DetailRepository) {
    suspend operator fun invoke(idHistory: String) : ResultUtil<DetailResponse> {
        return detailRepository.detailHistory(idHistory)

    }
}