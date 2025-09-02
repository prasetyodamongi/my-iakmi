package com.pcoding.myiakmi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pcoding.myiakmi.data.api.ApiConfig
import com.pcoding.myiakmi.data.model.RegistrasiKeanggotaan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RegistrasiKeanggotaanViewModel : ViewModel() {
    private val apiService = ApiConfig.createApiClientMember()

    private val _postSuccess = MutableStateFlow(false)
    val postSuccess: StateFlow<Boolean> = _postSuccess

    private val _postError = MutableStateFlow(false)
    val postError: StateFlow<Boolean> = _postError

    fun fetchRegister(member: RegistrasiKeanggotaan, pdfFiles: List<File>) {
        viewModelScope.launch {
            try {
                val json = Gson().toJson(member)
                val requestBodyJson = json.toRequestBody("application/json".toMediaTypeOrNull())

                val pdfParts = pdfFiles.map { file ->
                    val pdfRequestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("pdfFiles", file.name, pdfRequestBody)
                }

                val response = apiService.registerMember(requestBodyJson, pdfParts)
                if (response.status == "success") {
                    _postSuccess.value = true
                    _postError.value = false
                    Log.d("RegistrasiKeanggotaanViewModel", "message: ${response.message}")
                } else {
                    _postSuccess.value = false
                    _postError.value = true
                    Log.d("RegistrasiKeanggotaanViewModel", "message: ${response.message}")
                }
            } catch (e: Exception) {
                _postSuccess.value = false
                _postError.value = true
                Log.d("RegistrasiKeanggotaanViewModel", "Error: $e")
            }
        }
    }

    fun resetError() {
        _postError.value = false
    }
}