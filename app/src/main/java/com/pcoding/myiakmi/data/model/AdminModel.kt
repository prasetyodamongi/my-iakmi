package com.pcoding.myiakmi.data.model

data class PostQrResult(
    val qrcode: String,
    val userid: String,
    val aksi: String
)

data class QrResultResponse(
    val status: String,
    val message: String,
    val qrcode: String
)