package com.pcoding.myiakmi.data.model

data class UserData(
    val keanggotaan: String?,
    val email: String?,
    val userid: String?,
    val lengkap: String?,
    val isadmin: Int,
    val islogin: Boolean,
    val notactiveyet: Boolean,
    val member: Int
)

data class UserModel(
    val name: String,
    val address: String,
    val gender: String,
    val institution: String,
    val email: String,
    val phone: String,
    val password: String
)

data class RegisterResponse(
    val status: String,
    val message: String
)
