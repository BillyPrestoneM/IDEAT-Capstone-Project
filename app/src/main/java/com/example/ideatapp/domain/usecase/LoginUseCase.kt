package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository)  {
    suspend operator fun invoke(email: String, password: String) : Flow<ResultUtil<List<RegisterResponse>>> {
        return authRepository.login(email, password)
    }
}