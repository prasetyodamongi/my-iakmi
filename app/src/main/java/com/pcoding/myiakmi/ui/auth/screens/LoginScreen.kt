package com.pcoding.myiakmi.ui.auth.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.auth.nav.AuthNavItem
import com.pcoding.myiakmi.ui.components.ButtonCustom
import com.pcoding.myiakmi.ui.components.InputType
import com.pcoding.myiakmi.ui.components.TextInput
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pcoding.myiakmi.ui.components.dialogs.DialogResetPassword
import com.pcoding.myiakmi.ui.main.activity.MainActivity

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val loginSuccess by viewModel.loginSuccess.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val emailFocusRequester = FocusRequester()
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    var showDialogResetPass by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.restError()
    }
    MyIAKMITheme(statusBarColor = colorResource(R.color.white), darkTheme = true) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(R.color.white))
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.login),
                    modifier = modifier.align(Alignment.Center),
                    color = colorResource(R.color.main_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.email),
                    color = colorResource(R.color.main_color),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    InputType.User,
                    value = email,
                    onValueChange = { email = it },
                    focusRequester = emailFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        passwordFocusRequester.requestFocus()
                    }),
                    onError = { emailError = it }
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.password),
                    color = colorResource(R.color.main_color),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium))
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    InputType.PasswordLogin,
                    value = password,
                    onValueChange = { password = it },
                    focusRequester = passwordFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    onError = { passwordError = it }
                )
                Spacer(modifier = modifier.height(8.dp))
                Box(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.lupa_password),
                        modifier = modifier
                            .align(Alignment.CenterEnd)
                            .clickable {
                                showDialogResetPass = true
                            },
                        color = colorResource(R.color.text_color1),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                }
                Spacer(modifier = modifier.height(16.dp))
                ButtonCustom(
                    btnText = stringResource(R.string.login),
                    textColor = Color.White,
                    btnColor = colorResource(R.color.main_color),
                    isLoading = isLoading,
                    onClick = {
                        when {
                            email.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.email_harus_diisi),
                                    Toast.LENGTH_SHORT
                                ).show()
                                emailFocusRequester.requestFocus()
                            }

                            password.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.password_harus_diisi),
                                    Toast.LENGTH_SHORT
                                ).show()
                                passwordFocusRequester.requestFocus()
                            }

                            emailError.isNotEmpty() -> {
                                Toast.makeText(context, emailError, Toast.LENGTH_SHORT).show()
                                emailFocusRequester.requestFocus()
                            }

                            passwordError.isNotEmpty() -> {
                                Toast.makeText(context, passwordError, Toast.LENGTH_SHORT).show()
                                passwordFocusRequester.requestFocus()
                            }

                            else -> {
                                isLoading = true
                                viewModel.performLogin(email, password)
                            }
                        }
                    }
                )

                Spacer(modifier = modifier.height(16.dp))
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.bukan_anggota_iakmi_tanya),
                        color = colorResource(R.color.text_color1),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.buat_akun_non_anggota),
                        modifier = modifier.clickable {
                            navController.navigate(AuthNavItem.RegisterNonAnggota.route)
                        },
                        color = colorResource(R.color.main_color),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                }
            }
        }
    }

    if (loginError.isNotEmpty()) {
        isLoading = false
        if (loginError == "error1") {
            Toast.makeText(
                context,
                context.getString(R.string.username_atau_password_salah),
                Toast.LENGTH_SHORT
            ).show()
        } else if (loginError == "error2") {
            Toast.makeText(
                context,
                context.getString(R.string.terjadi_kesalahan_coba_lagi),
                Toast.LENGTH_SHORT
            ).show()
        }
        viewModel.restError()
    }


    if (loginSuccess) {
        isLoading = false
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as? Activity)?.finish()
        email = ""
        password = ""
    }

    if (showDialogResetPass) {
        DialogResetPassword(true) {
            showDialogResetPass = false
        }
    }
}