package com.pcoding.myiakmi.ui.main.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.auth.AuthActivity
import com.pcoding.myiakmi.ui.components.SettingItem
import com.pcoding.myiakmi.ui.components.SettingItemClickable
import com.pcoding.myiakmi.ui.components.dialogs.DialogGantiBahasa
import com.pcoding.myiakmi.ui.components.dialogs.DialogKonfirmasi
import com.pcoding.myiakmi.ui.components.dialogs.DialogNotifikasi
import com.pcoding.myiakmi.ui.components.dialogs.DialogResetPassword
import com.pcoding.myiakmi.ui.main.activity.AboutActivity
import com.pcoding.myiakmi.ui.main.activity.HelpActivity
import com.pcoding.myiakmi.ui.main.activity.PrivacyPolicyActivity
import com.pcoding.myiakmi.ui.main.activity.TermsConditionsActivity
import com.pcoding.myiakmi.ui.main.activity.TermsConditionsScreen
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.utils.setLocale
import com.pcoding.myiakmi.viewmodel.AuthViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    var showDialog by remember { mutableStateOf(false) }
    var showDialogBahasa by remember { mutableStateOf(false) }
    var showDialogResetPass by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("id") }

    MyIAKMITheme(statusBarColor = colorResource(R.color.white), darkTheme = true) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            // Top Bar
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.title_topbar_setting),
                    modifier = modifier.align(Alignment.Center),
                    color = colorResource(R.color.main_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center
                )
            }

            // Konten utama
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = stringResource(R.string.my_account),
                    modifier = modifier.padding(start = 16.dp, bottom = 12.dp),
                    color = colorResource(R.color.main_color),
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 18.sp
                )
                SettingItem(stringResource(R.string.member_status), "${userData.keanggotaan}")
                SettingItem(
                    if (userData.isadmin == 1) stringResource(R.string.username) else stringResource(R.string.email),
                    "${userData.email}"
                )
                SettingItemClickable(
                    stringResource(R.string.password_reset),
                    stringResource(R.string.subtitle_password_reset)
                ) {
                    showDialogResetPass = true
                }
                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.about),
                    modifier = modifier.padding(start = 16.dp, bottom = 12.dp),
                    color = colorResource(R.color.main_color),
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 18.sp
                )
                SettingItemClickable(
                    stringResource(R.string.about),
                    stringResource(R.string.subtitle_about)
                ) {
                    context.startActivity(Intent(context, AboutActivity::class.java))
                }
                SettingItemClickable(
                    stringResource(R.string.help),
                    stringResource(R.string.subtitle_help)
                ) {
                    context.startActivity(Intent(context, HelpActivity::class.java))
                }
                SettingItemClickable(
                    stringResource(R.string.terms_and_condition),
                    stringResource(R.string.subtitle_tc)
                ) {
                    context.startActivity(Intent(context, TermsConditionsActivity::class.java))
                }
                SettingItemClickable(
                    stringResource(R.string.privacy_policy),
                    stringResource(R.string.subtitle_pp)
                ) {
                    context.startActivity(Intent(context, PrivacyPolicyActivity::class.java))
                }
                Spacer(modifier = modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.other),
                    modifier = modifier.padding(start = 16.dp, bottom = 12.dp),
                    color = colorResource(R.color.main_color),
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontSize = 18.sp
                )
                SettingItemClickable(
                    stringResource(R.string.language),
                    stringResource(R.string.subtitle_language)
                ) {
                    showDialogBahasa = true
                }
                SettingItemClickable(
                    stringResource(R.string.logout),
                    stringResource(R.string.subtitle_logout)
                ) {
                    showDialog = true
                }
            }
        }
    }

    if (showDialogBahasa) {
        DialogGantiBahasa(
            onLanguageChange = { selectedLanguage ->
                language = selectedLanguage
                setLocale(context, selectedLanguage)
            },
            onDismiss = { showDialogBahasa = false }
        )
    }

    if (showDialog) {
        DialogKonfirmasi(
            teks = "Anda yakin ingin logout?",
            onConfirm = {
                viewModel.logout()
                context.startActivity(Intent(context, AuthActivity::class.java))
                (context as? Activity)?.finish()
            },
            onDismiss = { showDialog = false }
        )
    }

    if (showDialogResetPass) {
        if (userData.isadmin == 1) {
            DialogNotifikasi(
                title = stringResource(R.string.app_name),
                teks = stringResource(R.string.reset_pass_message_admin)
            ) {
                showDialogResetPass = false
            }
        } else {
            DialogResetPassword(false) {
                showDialogResetPass = false
            }

        }

    }
}