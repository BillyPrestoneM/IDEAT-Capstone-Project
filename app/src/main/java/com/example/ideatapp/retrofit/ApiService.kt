package com.example.ideatapp.retrofit

import com.example.ideatapp.login.LoginRequestBody
import com.example.ideatapp.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    fun login(@Body loginRequestBody: LoginRequestBody): Call<LoginResponse>
}