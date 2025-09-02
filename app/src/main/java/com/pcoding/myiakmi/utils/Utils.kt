package com.pcoding.myiakmi.utils

import android.content.Context
import java.util.Locale

fun setLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
    saveLanguagePreference(context, language)
}

fun saveLanguagePreference(context: Context, language: String) {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    prefs.edit().putString("language", language).apply()
}

fun getSavedLanguagePreference(context: Context): String {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return prefs.getString("language", "id") ?: "id"
}