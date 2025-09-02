package com.pcoding.myiakmi.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pcoding.myiakmi.ui.auth.nav.AuthNavGraph
import com.pcoding.myiakmi.ui.main.activity.MainActivity
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.utils.getSavedLanguagePreference
import com.pcoding.myiakmi.utils.setLocale

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val savedLanguage = getSavedLanguagePreference(context)
        setLocale(context, savedLanguage)

        if (SessionManager.isLoggedIn(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            AuthNavGraph()
        }
    }
}