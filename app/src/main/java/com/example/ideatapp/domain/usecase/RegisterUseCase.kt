package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(nama: String, email: String, password: String, confirmPassword: String) : Flow<ResultUtil<List<RegisterResponse>>> {
        return authRepository.register(nama, email, password, confirmPassword )
    }
}