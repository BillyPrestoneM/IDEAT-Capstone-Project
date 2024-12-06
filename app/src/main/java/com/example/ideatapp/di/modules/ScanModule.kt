package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.ScanImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.ScanRepository
import com.example.ideatapp.domain.usecase.ScanUseCase
import com.example.ideatapp.presentation.viewmodel.ScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val scanModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<ScanRepository> {ScanImpl(get())}
    single { ScanUseCase(get()) }

    viewModel{ ScannerViewModel(get()) }

}