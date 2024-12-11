package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.EditProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.EditProfileRepository
import kotlinx.coroutines.flow.Flow

class EditProfileUseCase(private val editProfileRepository: EditProfileRepository) {
    suspend operator fun invoke (nama: String, jenisKelamin: String, tanggalLahir: String, beratBadan: Int, tinggiBadan: Int) : Flow<ResultUtil<List<EditProfileResponse>>> {
        return editProfileRepository.editProfile(nama, jenisKelamin, tanggalLahir, beratBadan, tinggiBadan)

    }
}