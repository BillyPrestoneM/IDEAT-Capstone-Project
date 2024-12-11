package com.example.ideatapp.domain.usecase

import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow

class GetEmailUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(): Flow<String?> {
        return tokenRepository.getEmail()
    }
}
