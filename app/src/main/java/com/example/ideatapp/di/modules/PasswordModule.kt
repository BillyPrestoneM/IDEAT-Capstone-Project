package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.PasswordRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.PasswordRepository
import com.example.ideatapp.domain.usecase.PasswordUseCase
import com.example.ideatapp.presentation.viewmodel.PasswordViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val PasswordModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<PasswordRepository> { PasswordRepositoryImpl(get(), get()) }
    single { PasswordUseCase(get()) }

    viewModel { PasswordViewModelImpl(get(), get()) }



}