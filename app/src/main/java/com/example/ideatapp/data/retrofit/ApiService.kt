package com.example.ideatapp.data.retrofit
import com.example.ideatapp.data.model.DataProfileResponse
import com.example.ideatapp.data.model.DetailResponse
import com.example.ideatapp.data.model.EditProfileResponse
import com.example.ideatapp.data.model.HistoryResponse
import com.example.ideatapp.data.model.LoginResponse
import com.example.ideatapp.data.model.PasswordResponse
import com.example.ideatapp.data.model.RegisterResponse
import com.example.ideatapp.data.model.ScanResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun historyCall(): HistoryResponse

    @GET("historyScan/{idHistory}")
    suspend fun detailCall(@Path("idHistory") idHistory: String): Response<DetailResponse>

    @GET("profile")
    suspend fun profileCall(
        @Query("nama") nama: String,
        @Query("email") email: String,
        @Query("jenisKelamin") jenisKelamin: String,
        @Query("tanggalLahir") tanggalLahir: String,
        @Query("beratBadan") beratBadan: Int,
        @Query("tinggiBadan") tinggiBadan: Int
    ): Response<DataProfileResponse>

    @FormUrlEncoded
    @PUT("profile")
    suspend fun editProfileCall(@Field("nama") nama: String, @Field("jenisKelamin") jenisKelamin: String, @Field("tanggalLahir") tanggalLahir: String, @Field("beratBadan") beratBadan: Int, @Field("tinggiBadan") tinggiBadan: Int
    ): Response<EditProfileResponse>

    @FormUrlEncoded
    @PUT("password")
    suspend fun changePasswordCall(@Field("oldPassword") oldPassword: String, @Field("newPassword") newPassword: String, @Field("confirmNewPassword") confirmNewPassword: String
    ): Response<PasswordResponse>

}
