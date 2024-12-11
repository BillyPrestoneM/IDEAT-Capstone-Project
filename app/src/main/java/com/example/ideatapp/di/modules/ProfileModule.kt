package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.ProfileRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.ProfileRepository
import com.example.ideatapp.domain.usecase.ProfileUseCase
import com.example.ideatapp.presentation.viewmodel.ProfileViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ProfileModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single { ProfileUseCase(get()) }

    viewModel { ProfileViewModelImpl (get()) }

}