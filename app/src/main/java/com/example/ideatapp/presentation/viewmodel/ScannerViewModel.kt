package com.example.ideatapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.ScanResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.ScanUseCase
import kotlinx.coroutines.launch
import java.io.File

class ScannerViewModel(private val scanUseCase: ScanUseCase) : ViewModel() {

    private val _scanResult = MutableLiveData<ResultUtil<ScanResponse>>()
    val scanResult: LiveData<ResultUtil<ScanResponse>> = _scanResult

    private val _navigateToRiwayat = MutableLiveData(false)
    val navigateToRiwayat: LiveData<Boolean> = _navigateToRiwayat

    fun uploadImage(file: File) {
        _scanResult.value = ResultUtil.Loading
        viewModelScope.launch {
            try {
                scanUseCase(file).collect { result ->
                    _scanResult.value = result
                    if (result is ResultUtil.Success) {
                        setNavigateToRiwayat(true)
                    }
                }
            } catch (e: Exception) {
                _scanResult.value = ResultUtil.Error("Unexpected error: ${e.message}")
            }
        }
    }

    fun setNavigateToRiwayat(value: Boolean) {
        _navigateToRiwayat.value = value
    }
}

