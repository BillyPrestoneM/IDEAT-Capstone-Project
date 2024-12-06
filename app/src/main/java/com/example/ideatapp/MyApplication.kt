package com.example.ideatapp

import android.app.Application
import com.example.ideatapp.di.modules.DetailModule
import com.example.ideatapp.di.modules.HomeModule
import com.example.ideatapp.di.modules.dataStoreModule
import com.example.ideatapp.di.modules.scanModule
import com.example.ideatapp.di.modules.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger(Level.ERROR)
            modules(listOf(dataStoreModule, userModule, scanModule, HomeModule, DetailModule))
        }
    }
}