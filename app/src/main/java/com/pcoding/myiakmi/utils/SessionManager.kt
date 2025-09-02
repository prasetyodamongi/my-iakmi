package com.pcoding.myiakmi.utils

import android.content.Context
import androidx.core.content.edit
import com.pcoding.myiakmi.data.model.LoginResponse
import com.pcoding.myiakmi.data.model.UserData

object SessionManager {
    private const val PREF_NAME = "MyAppPref"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_KEANGGOTAAN = "keanggotaan"
    private const val KEY_EMAIL = "email"
    private const val KEY_USER_ID = "userId"
    private const val KEY_LENGKAP = "lengkap"
    private const val KEY_IS_ADMIN = "isAdmin"
    private const val KEY_IS_LOGIN = "isLogin"
    private const val KEY_NOT_ACTIVE_YET = "notActiveYet"
    private const val KEY_MEMBER = "member"

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    fun setLoginData(context: Context, loginResponse: LoginResponse) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(KEY_KEANGGOTAAN, loginResponse.keanggotaan)
            putString(KEY_EMAIL, loginResponse.email)
            putString(KEY_USER_ID, loginResponse.userid)
            putString(KEY_LENGKAP, loginResponse.lengkap)
            putInt(KEY_IS_ADMIN, loginResponse.isadmin)
            putBoolean(KEY_IS_LOGIN, loginResponse.islogin)
            putBoolean(KEY_NOT_ACTIVE_YET, loginResponse.notactiveyet)
            putInt(KEY_MEMBER, loginResponse.member)
            apply()
        }
    }

    fun getUserData(context: Context): UserData {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return UserData(
            keanggotaan = sharedPreferences.getString(KEY_KEANGGOTAAN, null),
            email = sharedPreferences.getString(KEY_EMAIL, null),
            userid = sharedPreferences.getString(KEY_USER_ID, null),
            lengkap = sharedPreferences.getString(KEY_LENGKAP, null),
            isadmin = sharedPreferences.getInt(KEY_IS_ADMIN, 0),
            islogin = sharedPreferences.getBoolean(KEY_IS_LOGIN, false),
            notactiveyet = sharedPreferences.getBoolean(KEY_NOT_ACTIVE_YET, false),
            member = sharedPreferences.getInt(KEY_MEMBER, 0)
        )
    }

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_KEANGGOTAAN)
            remove(KEY_EMAIL) // Hapus KEY_EMAIL saat logout
            remove(KEY_USER_ID)
            remove(KEY_LENGKAP)
            remove(KEY_IS_ADMIN)
            remove(KEY_IS_LOGIN)
            remove(KEY_NOT_ACTIVE_YET)
            remove(KEY_MEMBER)
            apply()
        }
    }
}