package com.example.ideatapp.domain.usecase

import com.example.ideatapp.data.model.DataProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(
        nama : String,
        email : String,
        jenisKelamin : String,
        tanggalLahir : String,
        beratBadan : Int,
        tinggiBadan : Int
    ) : Flow<ResultUtil<List<DataProfileResponse>>> {
        return profileRepository.dataProfile(nama, email, jenisKelamin, tanggalLahir, beratBadan, tinggiBadan)
    }
}