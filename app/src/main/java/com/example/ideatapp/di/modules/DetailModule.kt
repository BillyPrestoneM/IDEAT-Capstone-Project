package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.DetailRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.DetailRepository
import com.example.ideatapp.domain.usecase.DetailUseCase
import com.example.ideatapp.presentation.viewmodel.DetailViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DetailModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<DetailRepository> { DetailRepositoryImpl(get(), get()) }
    single { DetailUseCase(get()) }
    viewModel { DetailViewModelImpl(get(), get()) }
}