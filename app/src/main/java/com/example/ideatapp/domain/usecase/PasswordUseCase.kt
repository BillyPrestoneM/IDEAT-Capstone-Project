package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.PasswordResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow

class PasswordUseCase(private val passwordRepository: PasswordRepository) {
    suspend operator fun invoke(oldPassword: String, newPassword: String, confirmNewPassword: String) : Flow<ResultUtil<List<PasswordResponse>>> {
        return passwordRepository.changePassword(oldPassword, newPassword, confirmNewPassword)

    }
}