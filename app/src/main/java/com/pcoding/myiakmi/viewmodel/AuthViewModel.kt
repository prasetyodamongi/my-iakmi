package com.pcoding.myiakmi.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.data.api.ApiConfig
import com.pcoding.myiakmi.data.model.LoginModel
import com.pcoding.myiakmi.data.model.ResetPassword
import com.pcoding.myiakmi.data.model.UserModel
import com.pcoding.myiakmi.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = ApiConfig.createApiClient()

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _registerError = MutableStateFlow("")
    val registerError: StateFlow<String> = _registerError

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _loginError = MutableStateFlow("")
    val loginError: StateFlow<String> = _loginError

    private val _resetPassSuccess = MutableStateFlow(false)
    val resetPassSuccess: StateFlow<Boolean> = _resetPassSuccess

    private val _resetPassError = MutableStateFlow(false)
    val resetPassError: StateFlow<Boolean> = _resetPassError

    init {
        checkLoginStatus()
    }

    fun performRegister(
        name: String,
        address: String,
        gender: String,
        institution: String,
        email: String,
        phone: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val responseRegister = apiService.registerPerform(
                    UserModel(name, address, gender, institution, email, phone, password)
                )

                when (responseRegister.message) {
                    "Pendaftar Berhasil" -> {
                        _registerSuccess.value = true
                        Log.d("AuthViewModel", "Registration successful: ${responseRegister.message}")
                    }
                    "Email anda sudah terdaftar sebagai Non Member" -> {
                        _registerSuccess.value = false
                        _registerError.value = context.getString(R.string.email_sudah_terdaftar)
                        Log.d("AuthViewModel", "Registration failed: ${responseRegister.message}")
                    }
                    else -> {
                        _registerSuccess.value = false
                        context.getString(R.string.terjadi_kesalahan_tidak_diketahui)
                            .also { _registerError.value = it }
                        Log.d("AuthViewModel", "Registration failed: ${responseRegister.message}")
                    }
                }
            } catch (e: Exception) {
                // Tangani kesalahan di sini
                _registerSuccess.value = false
                _registerError.value = context.getString(R.string.terjadi_kesalahan) + e
                Log.d("AuthViewModel", "Error: $e")
            }
        }
    }

    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val aksi = "login"
                val responseLogin = apiService.loginPerform(LoginModel(username, password, aksi))
                if (responseLogin.status == "success") {
                    SessionManager.setLoggedIn(context, true)
                    SessionManager.setLoginData(context, responseLogin)
                    _loginSuccess.value = true
                    _loginError.value = ""
                    Log.d("AuthViewModel", "Login successful: ${responseLogin.message}")
                } else {
                    _loginError.value = "error1"
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _loginError.value = "error2"
                _loginSuccess.value = false
                Log.d("AuthViewModel", "Error: $e")
            }
        }
    }

    private fun checkLoginStatus() {
        if (SessionManager.isLoggedIn(context)) {
            _loginSuccess.value = true
        }
    }

    fun restError() {
        _loginError.value = ""
    }

    fun logout() {
        SessionManager.logout(context)
        _loginSuccess.value = false
    }

    fun resetPassword(email: String, aksi: String) {
        viewModelScope.launch {
            try {
                val response = apiService.resetPassword(ResetPassword(email, aksi))
                if (response.status == "success") {
                    _resetPassSuccess.value = true
                    _resetPassError.value = false
                    Log.d("AuthViewModel", "status: ${response.status}")
                } else {
                    _resetPassSuccess.value = false
                    _resetPassError.value = true
                    Log.d("AuthViewModel", "status: ${response.status}")
                }
            } catch (e: Exception) {
                _resetPassError.value = true
                Log.d("AuthViewModel", "error: $e")
            }
        }
    }

    fun resetErrorResetPass() {
        _resetPassError.value = false
    }
}