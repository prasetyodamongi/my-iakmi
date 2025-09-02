package com.pcoding.myiakmi.ui.main.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pcoding.myiakmi.ui.main.nav.BottomNavigationBar
import com.pcoding.myiakmi.ui.main.screens.HomeScreen
import com.pcoding.myiakmi.ui.main.screens.SettingScreen
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.utils.getSavedLanguagePreference
import com.pcoding.myiakmi.utils.setLocale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyIAKMITheme {
                val context = LocalContext.current
                val savedLanguage = getSavedLanguagePreference(context)
                LaunchedEffect(Unit) {
                    setLocale(context, savedLanguage)
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }

        SessionManager.setLoggedIn(this, true)
    }
}

@Composable
fun MyApp() {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedItem) { index ->
                selectedItem = index
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                0 -> HomeScreen()
                1 -> {}
                2 -> SettingScreen()
            }
        }
    }
}
