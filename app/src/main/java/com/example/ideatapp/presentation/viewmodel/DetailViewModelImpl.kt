package com.example.ideatapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.DetailRiwayatResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.DetailUseCase
import com.example.ideatapp.domain.usecase.GetTokenUseCase
import kotlinx.coroutines.launch

class DetailViewModelImpl(
    private val detailUseCase: DetailUseCase,
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _detailHistory = MutableLiveData<ResultUtil<DetailRiwayatResponse>>()
    val detailHistory: MutableLiveData<ResultUtil<DetailRiwayatResponse>> = _detailHistory

    fun fetchDetailHistory(idHistory: String) {
        viewModelScope.launch {
            val token = getTokenUseCase()
            if (token == null) {
                _detailHistory.value = ResultUtil.Error("Token not found")
                return@launch
            }

            try {
                _detailHistory.value = ResultUtil.Loading
                val result = detailUseCase(idHistory)
                _detailHistory.value = result
            } catch (e: Exception) {
                _detailHistory.value = ResultUtil.Error("Request failed: ${e.message}")
            }
        }
    }
}