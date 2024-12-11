package com.example.ideatapp.di.modules

import com.example.ideatapp.data.repository.EditProfileRepositoryImpl
import com.example.ideatapp.data.retrofit.ApiConfig
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.domain.repository.EditProfileRepository
import com.example.ideatapp.domain.usecase.EditProfileUseCase
import com.example.ideatapp.presentation.viewmodel.EditProfileViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val EditProfileModule = module {
    single<ApiService> { ApiConfig.getApiService() }
    single<EditProfileRepository> { EditProfileRepositoryImpl(get(), get()) }
    single{ EditProfileUseCase(get()) }

    viewModel { EditProfileViewModelImpl(get(), get()) }
}