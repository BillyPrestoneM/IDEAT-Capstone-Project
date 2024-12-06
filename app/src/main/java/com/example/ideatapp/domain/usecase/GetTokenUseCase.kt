package com.example.ideatapp.domain.usecase

import com.example.ideatapp.domain.repository.AuthRepository

class GetTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String? {
        return authRepository.getToken()
    }
}