package com.example.ideatapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.EditProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.EditProfileUseCase
import com.example.ideatapp.domain.repository.TokenRepository
import kotlinx.coroutines.launch

class EditProfileViewModelImpl(
    private val editProfileUseCase: EditProfileUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _editResult = MutableLiveData<ResultUtil<List<EditProfileResponse>>>()
    val editResult: MutableLiveData<ResultUtil<List<EditProfileResponse>>> = _editResult

    fun editProfile(
        nama: String,
        jenisKelamin: String,
        tanggalLahir: String,
        beratBadan: Int,
        tinggiBadan: Int
    ) {
        viewModelScope.launch {
            try {
                _editResult.value = ResultUtil.Loading
                val token = tokenRepository.getToken()
                if (token == null) {
                    _editResult.value = ResultUtil.Error("Tidak ada Token, Silahkan Login Kembali")
                    return@launch
                }
                editProfileUseCase(nama, jenisKelamin, tanggalLahir, beratBadan, tinggiBadan)
                    .collect { result ->
                        _editResult.value = result
                    }

            } catch (e: Exception) {
                _editResult.value = ResultUtil.Error("Exception occurred: ${e.message}")
            }
        }
    }
}
