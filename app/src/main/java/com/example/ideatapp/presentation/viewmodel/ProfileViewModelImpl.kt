package com.example.ideatapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.DataProfileResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.ProfileUseCase
import kotlinx.coroutines.launch

class ProfileViewModelImpl(
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {

    private val _profileResult = MutableLiveData<ResultUtil<List<DataProfileResponse>>>(ResultUtil.Loading)
    val profileResult: LiveData<ResultUtil<List<DataProfileResponse>>> get() = _profileResult

    fun fetchProfile(
        nama: String,
        email: String,
        jenisKelamin: String,
        tanggalLahir: String,
        beratBadan: Int,
        tinggiBadan: Int
    ) {
        viewModelScope.launch {
            try {
                profileUseCase(
                    nama,
                    email,
                    jenisKelamin,
                    tanggalLahir,
                    beratBadan,
                    tinggiBadan
                ).collect { result ->
                    _profileResult.value = result
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile: ${e.message}")
                _profileResult.value = ResultUtil.Error("Failed to fetch profile")
            }
        }
    }
}
