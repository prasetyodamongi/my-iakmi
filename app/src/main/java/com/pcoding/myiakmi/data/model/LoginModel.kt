package com.pcoding.myiakmi.data.model

data class LoginModel(
    val email: String,
    val password: String,
    val aksi: String
)

data class LoginResponse(
    val status: String,
    val message: String,
    val keanggotaan: String,
    val email: String,
    val userid: String,
    val lengkap: String,
    val isadmin: Int,
    val islogin: Boolean,
    val notactiveyet: Boolean,
    val member: Int
)

data class ResetPassword(
    val email: String,
    val aksi: String
)

data class ResetPasswordResponse(
    val status: String,
    val message: String
)