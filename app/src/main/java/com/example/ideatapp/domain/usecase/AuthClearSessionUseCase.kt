package com.example.ideatapp.domain.usecase

import com.example.ideatapp.domain.repository.TokenRepository

class AuthClearSessionUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke() {
        tokenRepository.clearSession()
    }
}
