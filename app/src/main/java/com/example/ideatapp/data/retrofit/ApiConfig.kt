package com.example.ideatapp.data.retrofit

import com.example.ideatapp.BuildConfig
import com.example.ideatapp.data.storage.AuthPreferencesToken
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.mp.KoinPlatform.getKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val authPreferences: AuthPreferencesToken = getKoin().get()
            val authInterceptor = AuthInterceptor(authPreferences)
            val baseUrlApi = BuildConfig.BASE_URL
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrlApi)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}