package com.example.ideatapp.data.retrofit
import com.example.ideatapp.data.model.DetailRiwayatResponse
import com.example.ideatapp.data.model.LoginResponse
import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.data.model.RiwayatResponse
import com.example.ideatapp.data.model.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun registerCall(@Field("nama") nama:String, @Field("email") email:String, @Field("password") password:String, @Field("confirmPassword") confirmPassword:String): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginCall(@Field("email") email:String, @Field("password") password:String): Response<LoginResponse>

    @Multipart
    @POST("scan")
    suspend fun scanCall(@Part file: MultipartBody.Part): Response<ScanResponse>

    @GET("historyScan")
    suspend fun historyCall(): RiwayatResponse

    @GET("historyScan/{idHistory}")
    suspend fun detailCall(@Path("idHistory") idHistory: String): Response<DetailRiwayatResponse>
}
