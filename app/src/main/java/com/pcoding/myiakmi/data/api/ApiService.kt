package com.pcoding.myiakmi.data.api

import com.pcoding.myiakmi.data.model.DetailEventResponse
import com.pcoding.myiakmi.data.model.EventResponse
import com.pcoding.myiakmi.data.model.LoginModel
import com.pcoding.myiakmi.data.model.LoginResponse
import com.pcoding.myiakmi.data.model.RegisterResponse
import com.pcoding.myiakmi.data.model.PostQrResult
import com.pcoding.myiakmi.data.model.QrResultResponse
import com.pcoding.myiakmi.data.model.RegistrasiKeanggotaan
import com.pcoding.myiakmi.data.model.RegistrasiKeanggotaanResponse
import com.pcoding.myiakmi.data.model.ReqDetailEvent
import com.pcoding.myiakmi.data.model.ResetPassword
import com.pcoding.myiakmi.data.model.ResetPasswordResponse
import com.pcoding.myiakmi.data.model.UserData
import com.pcoding.myiakmi.data.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @Headers("Content-Type: multipart/form-data")
    @POST("register/MobileDaftarAnggota")
    suspend fun registerMember(
        @Part("data") data: RequestBody,
        @Part pdfFiles: List<MultipartBody.Part>
    ): RegistrasiKeanggotaanResponse

    @Headers("Content-Type: Application/json")
    @POST("eventreg/daftarNonMember")
    suspend fun registerPerform(@Body user: UserModel): RegisterResponse

    @Headers("Content-Type: Application/json")
    @POST("auth/mobileLogin")
    suspend fun loginPerform(@Body login: LoginModel): LoginResponse

    @Headers("Content-Type: Application/json")
    @POST("auth/resetPasswordIakmi")
    suspend fun resetPassword(@Body resetPass: ResetPassword): ResetPasswordResponse

    @Headers("Content-Type: Application/json")
    @POST("eventreg/berandaApp")
    suspend fun requestEvent(@Body dataPost: UserData): EventResponse

    @Headers("Content-Type: Application/json")
    @POST("eventreg/registrasiKehadiran")
    suspend fun postRegistrasiKehadiran(@Body postregistrasiKehadiran: PostQrResult): QrResultResponse

    @Headers("Content-Type: Application/json")
    @POST("eventreg/detailEvent")
    suspend fun requestDetailEvent(@Body reqDetailEvent: ReqDetailEvent): DetailEventResponse
}