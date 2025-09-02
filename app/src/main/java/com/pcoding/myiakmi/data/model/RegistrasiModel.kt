package com.pcoding.myiakmi.data.model

import java.io.File

data class RegistrasiKeanggotaan(
    val nama: String,
    val email: String,
    val password: String
)

data class RegistrasiKeanggotaanResponse(
    val status: String,
    val message: String
)
