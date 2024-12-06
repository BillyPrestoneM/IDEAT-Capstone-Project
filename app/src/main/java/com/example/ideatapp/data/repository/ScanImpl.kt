package com.example.ideatapp.data.repository

import com.example.ideatapp.data.model.ScanResponse
import com.example.ideatapp.data.retrofit.ApiService
import com.example.ideatapp.di.utils.ResultUtil
import com.example.ideatapp.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ScanImpl(private val apiService : ApiService): ScanRepository {
    override suspend fun scanFood(file : File) : Flow<ResultUtil<ScanResponse>> {
        return flow{
            try{
                emit(ResultUtil.Loading)
                val imageRequestBody = file.asRequestBody("image/jpeg".toMediaType())
                val imagePart = MultipartBody.Part.createFormData("photo", file.name, imageRequestBody)

                val response = apiService.scanCall(imagePart)
                if (response.isSuccessful && response.body() != null) {
                    emit(ResultUtil.Success(response.body()!!))
                } else {
                    emit(ResultUtil.Error("Upload Failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ResultUtil.Error("Exception occurred: ${e.message}"))
            }
        }
    }
}