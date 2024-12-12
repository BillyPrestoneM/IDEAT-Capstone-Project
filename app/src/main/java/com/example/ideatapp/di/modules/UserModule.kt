package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.AuthRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.data.storage.AuthPreferencesToken
import com.example.ideatapp.domain.repository.AuthRepository
import com.example.ideatapp.domain.usecase.AuthClearSessionUseCase
import com.example.ideatapp.domain.usecase.AuthSaveTokenUseCase
import com.example.ideatapp.domain.usecase.GetTokenUseCase
import com.example.ideatapp.domain.usecase.GetUserNameUseCase
import com.example.ideatapp.domain.usecase.LoginUseCase
import com.example.ideatapp.domain.usecase.RegisterUseCase
import com.example.ideatapp.presentation.viewmodel.AuthViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single { GetTokenUseCase(get()) }
    single { LoginUseCase(get()) }
    single { AuthPreferencesToken(get()) }
    single { AuthSaveTokenUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { GetUserNameUseCase(get()) }
    single { AuthClearSessionUseCase(get()) }

    viewModel{ AuthViewModelImpl(get(), get(), get(), get(), get(), get()) }
}
