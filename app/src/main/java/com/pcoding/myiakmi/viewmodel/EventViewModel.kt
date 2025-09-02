package com.pcoding.myiakmi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.data.api.ApiConfig
import com.pcoding.myiakmi.data.model.Event
import com.pcoding.myiakmi.data.model.MemberDetails
import com.pcoding.myiakmi.data.model.ReqDetailEvent
import com.pcoding.myiakmi.data.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val apiService = ApiConfig.createApiClient()

    private val _dataEvent = MutableStateFlow<List<Event>>(emptyList())
    val dataEvent: StateFlow<List<Event>> = _dataEvent

    private val _detailMember = MutableStateFlow<MemberDetails?>(null)
    val detailMember: StateFlow<MemberDetails?> = _detailMember

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow("")
    val isError: StateFlow<String> = _isError

    fun reqEvent(
        keanggotaan: String?,
        email: String?,
        userid: String?,
        lengkap: String?,
        isadmin: Int,
        islogin: Boolean,
        notactiveyet: Boolean,
        member: Int
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.requestEvent(UserData(keanggotaan, email, userid, lengkap, isadmin, islogin, notactiveyet, member))
                if (response.status == "success") {
                    _isLoading.value = false
                    _dataEvent.value = response.events
                    Log.d("EventViewModel", "Response: $response")
                } else {
                    _isLoading.value = false
                    _isError.value = "error"
                    Log.d("EventViewModel", "Gagal memuat Data Event")
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _isError.value = "error"
                Log.d("EventViewModel", "Error: $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun reqDetailEvent(userid: String, member: String, evid: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.requestDetailEvent(ReqDetailEvent(userid, member, evid))
                if (response.status == "success") {
                    _detailMember.value = response.message
                    Log.d("EventViewModel", "Detail Event: $response")
                } else {
                    _isError.value = "error"
                    Log.d("EventViewModel", "Gagal memuat Detail Event")
                }
            } catch (e: Exception) {
                _isError.value = "error"
                Log.d("EventViewModel", "Error: $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetError() {
        _isError.value = ""
    }
}