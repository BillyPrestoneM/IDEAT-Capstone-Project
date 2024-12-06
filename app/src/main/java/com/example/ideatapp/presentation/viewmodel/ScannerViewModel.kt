package com.example.ideatapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.ScanUseCase
import kotlinx.coroutines.launch
import java.io.File

class ScannerViewModel(private val scanUseCase: ScanUseCase) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    private val _navigateHomeMain = MutableLiveData<Boolean>()
    val navigateHomeMain: LiveData<Boolean> = _navigateHomeMain

    fun scanViewModel(file: File) {
        _isLoading.value = true
        viewModelScope.launch {
            scanUseCase(file).collect { result ->
                when (result) {
                    is ResultUtil.Loading -> {
                        _isLoading.value = true
                    }

                    is ResultUtil.Error -> {
                        _isLoading.value = false
                        Log.e("ScannerViewModel", "Error: ${result.message}")
                        _errorMessage.value = result.message
                    }

                    is ResultUtil.Success -> {
                        _isLoading.value = false
                        _successMessage.value = "Scan berhasil ${result.data.message}"
                        _navigateHomeMain.value = true
                    }
                }
            }
        }
    }
}