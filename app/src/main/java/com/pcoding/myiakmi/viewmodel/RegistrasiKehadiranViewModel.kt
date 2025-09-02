package com.pcoding.myiakmi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pcoding.myiakmi.data.api.ApiConfig
import com.pcoding.myiakmi.data.model.PostQrResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrasiKehadiranViewModel : ViewModel() {
    private val apiService = ApiConfig.createApiClient()

    private val _postError = MutableStateFlow(false)
    val postError: StateFlow<Boolean> = _postError

    private val _postSuccess = MutableStateFlow(false)
    val postSuccess: StateFlow<Boolean> = _postSuccess

    fun postRegistrasiKehadiran(qrCode: String, userId: String, aksi: String) {
        viewModelScope.launch {
            try {
                val response = apiService.postRegistrasiKehadiran(PostQrResult(qrCode, userId, aksi))
                if (response.status == "success") {
                    _postSuccess.value = true
                    _postError.value = false
                    Log.d("RegistrasiKehadiranViewModel", "status: ${response.status}")
                    Log.d("RegistrasiKehadiranViewModel", "message: ${response.message}")
                    Log.d("RegistrasiKehadiranViewModel", "qrcode: ${response.qrcode}")
                } else {
                    _postSuccess.value = false
                    _postError.value = true
                }
            } catch (e: Exception) {
                _postSuccess.value = false
                _postError.value = true
                Log.d("RegistrasiKehadiranViewModel", "Error: $e")
            }
        }
    }

    fun resetAksi() {
        _postSuccess.value = false
        _postError.value = false
    }
}