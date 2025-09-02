package com.pcoding.myiakmi.data.model

data class EventResponse(
    val status: String,
    val message: String,
    val events: List<Event>
)

data class Event(
    val evid: String = "",
    val name: String = "",
    val location: String = "",
    val time: String ="",
)

data class ReqDetailEvent(
    val userid: String,
    val member: String,
    val evid: String
)

data class DetailEventResponse(
    val status: String,
    val message: MemberDetails
)

data class MemberDetails(
    val member: String,
    val statusRegistrasi: String?,
    val statusPembayaran: String,
    val jenisPeserta: String?,
    val abstrak: String?,
    val keterangan: String?,
    val presensi: String?,
    val flyer: String?
)
