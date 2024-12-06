package com.example.ideatapp.di.modules

import com.example.ideatapp.data.storage.AuthPreferencesToken
import com.example.ideatapp.data.storage.DataStoreFactory.createDataStore
import com.example.ideatapp.domain.repository.TokenRepository
import org.koin.dsl.module

val dataStoreModule = module{
    single{createDataStore(get(), "auth_token_prefs")}
    single<TokenRepository>{ AuthPreferencesToken(get()) }
}