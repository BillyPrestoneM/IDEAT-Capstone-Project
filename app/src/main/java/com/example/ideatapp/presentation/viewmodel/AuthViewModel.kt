package com.example.ideatapp.presentation.viewmodel

interface AuthViewModel {
    suspend fun login(email: String, password: String)

    suspend fun register(name: String, email: String, password: String, confirmPassword: String)

    suspend fun fetchUserName(): String?
}