package com.example.ideatapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.usecase.AuthClearSessionUseCase
import com.example.ideatapp.domain.usecase.AuthSaveTokenUseCase
import com.example.ideatapp.domain.usecase.GetEmailUseCase
import com.example.ideatapp.domain.usecase.GetTokenUseCase
import com.example.ideatapp.domain.usecase.GetUserNameUseCase
import com.example.ideatapp.domain.usecase.LoginUseCase
import com.example.ideatapp.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModelImpl(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getEmailUseCase: GetEmailUseCase,
    private val authSaveTokenUseCase: AuthSaveTokenUseCase,
    private val authClearSessionUseCase: AuthClearSessionUseCase
) : AuthViewModel, ViewModel() {

    private val _user = MutableStateFlow<ResultUtil<List<RegisterResponse>>?>(null)
    val user: StateFlow<ResultUtil<List<RegisterResponse>>?> = _user

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    override suspend fun login(email: String, password: String) {
        loginUseCase(email, password).collect { result ->
            _user.value = result

        }
    }

    override suspend fun register(nama: String, email: String, password: String, confirmPassword: String) {
        registerUseCase(nama, email, password, confirmPassword).collect { result ->
            _user.value = result
        }
    }

    override suspend fun fetchUserName(): String {
        getUserNameUseCase().collect { result ->
            _userName.value = result
            Log.d("AuthViewModel", "Username fetched: ${_userName.value}")
        }
        return userName.value ?: ""
    }

    suspend fun fetchUserEmail() {
        getEmailUseCase().collect { result ->
            _userEmail.value = result
            Log.d("AuthViewModel", "Email fetched: ${_userEmail.value}")
        }
    }

    fun fetchToken() {
        viewModelScope.launch {
            _token.value = getTokenUseCase()
        }
    }

    fun saveToken(token: String, name: String, email: String) {
        viewModelScope.launch {
            authSaveTokenUseCase(token, name, email)
        }
    }

    fun clearSession() {
        viewModelScope.launch {
            authClearSessionUseCase()
        }
    }
}
