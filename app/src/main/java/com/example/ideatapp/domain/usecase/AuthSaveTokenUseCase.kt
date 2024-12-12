package com.example.ideatapp.domain.usecase

import com.example.ideatapp.domain.repository.AuthRepository

class AuthSaveTokenUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token: String, name: String) {
        authRepository.saveAuthToken(token, name)
    }

}