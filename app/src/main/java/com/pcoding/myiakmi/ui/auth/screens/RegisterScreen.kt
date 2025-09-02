package com.pcoding.myiakmi.ui.auth.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.pcoding.myiakmi.ui.components.DropDownGender
import com.pcoding.myiakmi.ui.components.dialogs.DialogVerifikasiAkun
import com.pcoding.myiakmi.ui.components.InputType
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pcoding.myiakmi.ui.components.TextInput

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel:AuthViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val registerSuccess by viewModel.registerSuccess.collectAsState()
    val registerError by viewModel.registerError.collectAsState()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var fullName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var institution by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var noTlp by remember { mutableStateOf("") }
    var createPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var createPasswordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val fullNameFocusRequester = FocusRequester()
    val addressFocusRequester = FocusRequester()
    val genderFocusRequester = FocusRequester()
    val institutionFocusRequester = FocusRequester()
    val emailFocusRequester = FocusRequester()
    val noTlpFocusRequester = FocusRequester()
    val createPasswordFocusRequester = FocusRequester()
    val confirmPasswordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

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
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.kembali),
                        tint = colorResource(R.color.main_color)
                    )
                }
                Text(
                    text = stringResource(R.string.register),
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                CardRegisterMessage()
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.data_pribadi),
                    color = colorResource(R.color.main_color),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Normal,
                    hint = stringResource(R.string.masukan_nama_lengkap),
                    value = fullName,
                    onValueChange = { fullName = it },
                    icon = R.drawable.ic_person,
                    focusRequester = fullNameFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        addressFocusRequester.requestFocus()
                    })
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Normal,
                    hint = stringResource(R.string.masukan_alamat),
                    value = address,
                    onValueChange = { address = it },
                    icon = R.drawable.ic_map,
                    singleLine = false,
                    focusRequester = addressFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        genderFocusRequester.requestFocus()
                    })
                )
                Spacer(modifier = modifier.height(8.dp))
                DropDownGender(
                    onItemSelected = { gender = it },
                    focusRequester = genderFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        institutionFocusRequester.requestFocus()
                    })
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Normal,
                    hint = stringResource(R.string.masukan_instansi),
                    value = institution,
                    onValueChange = { institution = it },
                    icon = R.drawable.ic_intitution,
                    focusRequester = institutionFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        emailFocusRequester.requestFocus()
                    })
                )

                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.data_akun),
                    color = colorResource(R.color.main_color),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Email,
                    value = email,
                    onValueChange = { email = it },
                    focusRequester = emailFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        noTlpFocusRequester.requestFocus()
                    }),
                    onError = { emailError = it }
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Normal,
                    hint = stringResource(R.string.masukan_no_telp_hp_wa),
                    value = noTlp,
                    onValueChange = { noTlp = it },
                    icon = R.drawable.ic_call,
                    focusRequester = noTlpFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        createPasswordFocusRequester.requestFocus()
                    })
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Password,
                    value = createPassword,
                    onValueChange = { createPassword = it },
                    focusRequester = createPasswordFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        confirmPasswordFocusRequester.requestFocus()
                    }),
                    isCreatingPassword = true,
                    onError = { createPasswordError = it }
                )
                Spacer(modifier = modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Password,
                    hint = stringResource(R.string.konfirmasi_password),
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    icon = R.drawable.ic_password,
                    focusRequester = confirmPasswordFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    onError = { confirmPasswordError = it }
                )
                Spacer(modifier = modifier.height(16.dp))
                ButtonCustom(
                    btnText = stringResource(R.string.register),
                    textColor = Color.White,
                    btnColor = colorResource(R.color.main_color),
                    isLoading = isLoading,
                    onClick = {
                        when {
                            fullName.isEmpty() -> {
                                Toast.makeText(context,
                                    context.getString(R.string.nama_lengkap_harus_diisi), Toast.LENGTH_SHORT).show()
                                fullNameFocusRequester.requestFocus()
                            }
                            address.isEmpty() -> {
                                Toast.makeText(context,
                                    context.getString(R.string.alamat_harus_diisi), Toast.LENGTH_SHORT).show()
                                addressFocusRequester.requestFocus()
                            }
                            gender.isEmpty() -> {
                                Toast.makeText(context, context.getString(R.string.pilih_jenis_kelamin), Toast.LENGTH_SHORT).show()
                                genderFocusRequester.requestFocus()
                            }
                            institution.isEmpty() -> {
                                Toast.makeText(context, context.getString(R.string.institusi_harus_diisi), Toast.LENGTH_SHORT).show()
                                genderFocusRequester.requestFocus()
                            }
                            email.isEmpty() -> {
                                Toast.makeText(context, context.getString(R.string.email_harus_diisi), Toast.LENGTH_SHORT).show()
                                emailFocusRequester.requestFocus()
                            }
                            noTlp.isEmpty() -> {
                                Toast.makeText(context,
                                    context.getString(R.string.no_telepon_harus_diisi), Toast.LENGTH_SHORT).show()
                                noTlpFocusRequester.requestFocus()
                            }
                            createPassword.isEmpty() -> {
                                Toast.makeText(context, context.getString(R.string.password_harus_diisi), Toast.LENGTH_SHORT).show()
                                createPasswordFocusRequester.requestFocus()
                            }
                            confirmPassword.isEmpty() -> {
                                Toast.makeText(context,
                                    context.getString(R.string.konfirmasi_password_harus_diisi), Toast.LENGTH_SHORT).show()
                                confirmPasswordFocusRequester.requestFocus()
                            }
                            createPassword != confirmPassword -> {
                                Toast.makeText(context,
                                    context.getString(R.string.password_dan_konfirmasi_password_harus_sama), Toast.LENGTH_SHORT).show()
                                confirmPasswordFocusRequester.requestFocus()
                            }
                            emailError.isNotEmpty() -> {
                                Toast.makeText(context, emailError, Toast.LENGTH_SHORT).show()
                                emailFocusRequester.requestFocus()
                            }
                            createPasswordError.isNotEmpty() -> {
                                Toast.makeText(context, createPasswordError, Toast.LENGTH_SHORT).show()
                                createPasswordFocusRequester.requestFocus()
                            }
                            confirmPasswordError.isNotEmpty() -> {
                                Toast.makeText(context, confirmPasswordError, Toast.LENGTH_SHORT).show()
                                confirmPasswordFocusRequester.requestFocus()
                            }
                            else -> {
                                viewModel.performRegister(
                                    fullName,
                                    address,
                                    gender,
                                    institution,
                                    email,
                                    noTlp,
                                    confirmPassword
                                )
                                isLoading = true
                            }
                        }
                    }
                )
                Spacer(modifier = modifier.height(16.dp))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.sudah_punya_akun),
                        color = colorResource(R.color.text_color1),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.login),
                        modifier = modifier.clickable { navController.navigate(AuthNavItem.LoginNonAnggota.route) },
                        color = colorResource(R.color.main_color),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium))
                    )
                }
            }
        }
    }

    LaunchedEffect(registerError) {
        if (registerError.isNotBlank()) {
            isLoading = false
            Toast.makeText(context, registerError, Toast.LENGTH_SHORT).show()
        }
    }

    if (registerSuccess) {
        isLoading = false
        showDialog = true
        fullName = ""
        address = ""
        gender = ""
        institution = ""
        email = ""
        noTlp = ""
        createPassword = ""
        confirmPassword = ""
    }

    if (showDialog) {
        DialogVerifikasiAkun {
            showDialog = false
            navController.popBackStack()
        }
    }
}

@Composable
fun CardRegisterMessage(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.white)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "1.",
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.message1),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "2.",
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
                Spacer(modifier = modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.message2),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
            }
        }
    }
}