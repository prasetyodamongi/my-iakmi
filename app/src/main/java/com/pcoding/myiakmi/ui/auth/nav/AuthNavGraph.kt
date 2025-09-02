package com.pcoding.myiakmi.ui.auth.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pcoding.myiakmi.ui.auth.screens.LoginScreen
import com.pcoding.myiakmi.ui.auth.screens.RegisterMemberScreen
import com.pcoding.myiakmi.ui.auth.screens.RegisterScreen

@Composable
fun AuthNavGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AuthNavItem.LoginNonAnggota.route
    ) {
        composable(AuthNavItem.LoginNonAnggota.route) {
            LoginScreen(navController)
        }
        composable(AuthNavItem.RegisterNonAnggota.route) {
            RegisterScreen(navController)
        }
        composable(AuthNavItem.RegisterAnggota.route) {
            RegisterMemberScreen(navController)
        }
    }
}

enum class AuthScreen {
    LOGIN_NON_ANGGOTA,
    REGISTER_NON_ANGGOTA,
    REGISTER_ANGGOTA
}
sealed class AuthNavItem(val route: String) {
    data object LoginNonAnggota : AuthNavItem(AuthScreen.LOGIN_NON_ANGGOTA.name)
    data object RegisterNonAnggota : AuthNavItem(AuthScreen.REGISTER_NON_ANGGOTA.name)
    data object RegisterAnggota : AuthNavItem(AuthScreen.REGISTER_ANGGOTA.name)
}