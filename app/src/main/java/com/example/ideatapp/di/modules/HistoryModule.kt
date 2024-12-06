package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.HistoryRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.HistoryRepository
import com.example.ideatapp.domain.usecase.HistoryUseCase
import com.example.ideatapp.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val HomeModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single { HistoryUseCase(get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get(), get()) }
    viewModel { HomeViewModel(get(), get()) }
}