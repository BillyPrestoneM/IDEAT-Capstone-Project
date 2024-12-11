package com.example.ideatapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.PasswordResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.TokenRepository
import com.example.ideatapp.domain.usecase.PasswordUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PasswordViewModelImpl(
    private val passwordUseCase: PasswordUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _passwordChangeResult = MutableLiveData<ResultUtil<List<PasswordResponse>>>()
    val passwordChangeResult: MutableLiveData<ResultUtil<List<PasswordResponse>>> = _passwordChangeResult

    fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmNewPassword: String
    ) {
        viewModelScope.launch {
            try {
                _passwordChangeResult.value = ResultUtil.Loading
                val token = tokenRepository.getToken()
                if (token == null) {
                    _passwordChangeResult.value = ResultUtil.Error("Tidak ada Token, Silahkan Login Kembali")
                    return@launch
                }
                passwordUseCase(oldPassword, newPassword, confirmNewPassword).collect { result ->
                    _passwordChangeResult.value = result
                    if (result is ResultUtil.Success) {
                        _passwordChangeResult.value = ResultUtil.Error("Password berhasil diubah, silahkan login kembali.")
                    }
                }
            } catch (e: Exception) {
                _passwordChangeResult.value = ResultUtil.Error("Exception occurred: ${e.message}")
            }
        }
    }
}
