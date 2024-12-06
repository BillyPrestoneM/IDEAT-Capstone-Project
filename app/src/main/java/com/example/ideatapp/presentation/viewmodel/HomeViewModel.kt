package com.example.ideatapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.DataItem
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.GetTokenUseCase
import com.example.ideatapp.domain.usecase.HistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val historyUseCase: HistoryUseCase, private val tokenUseCase: GetTokenUseCase) : ViewModel() {

    private val _historyList = MutableLiveData<ResultUtil<List<DataItem>>>()
    val historyList: LiveData<ResultUtil<List<DataItem>>> get() = _historyList

    private val _token = MutableStateFlow<String?>(null)
    val token : StateFlow<String?> = _token

    fun fetchHistory() {
        viewModelScope.launch {
            if (tokenUseCase() == null) {
                _historyList.value = ResultUtil.Error("Token not found")
                Log.e("HomeViewModel", "Token not found")
                return@launch
            }
            historyUseCase().collect { result ->
                _historyList.value = result
            }
        }
    }
}