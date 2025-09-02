package com.pcoding.myiakmi.ui.auth.screens

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.pcoding.myiakmi.ui.components.ButtonCustom
import com.pcoding.myiakmi.ui.components.InputType
import com.pcoding.myiakmi.ui.components.TextInput
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.viewmodel.RegistrasiKehadiranViewModel
import java.io.File
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pcoding.myiakmi.data.form.FormDataAkun
import com.pcoding.myiakmi.data.form.FormDataDiri
import com.pcoding.myiakmi.data.model.RegistrasiKeanggotaan
import com.pcoding.myiakmi.viewmodel.RegistrasiKeanggotaanViewModel

@Composable
fun RegisterMemberScreen(
    navController: NavController,
    viewModel: RegistrasiKeanggotaanViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val postSuccess by viewModel.postSuccess.collectAsState()
    val postError by viewModel.postError.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var createPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var createPasswordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    var fullName by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var tempatLahir by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var kabupatenKota by remember { mutableStateOf("") }
    var kodePos by remember { mutableStateOf("") }
    var nomorWA by remember { mutableStateOf("") }
    var gelarS1 by remember { mutableStateOf("") }
    var gelarS2 by remember { mutableStateOf("") }
    var gelarS3 by remember { mutableStateOf("") }
    var daerahKepengurusan by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val emailFocusRequester = FocusRequester()
    val createPasswordFocusRequester = FocusRequester()
    val confirmPasswordFocusRequester = FocusRequester()
    val fullNameFocusRequester = FocusRequester()
    val nikFocusRequester = FocusRequester()
    val tempatLahirFocusRequester = FocusRequester()
    val tanggalLahirFocusRequester = FocusRequester()
    val alamatFocusRequester = FocusRequester()
    val kabupatenKotaFocusRequester = FocusRequester()
    val kodePosFocusRequester = FocusRequester()
    val nomorWAFocusRequester = FocusRequester()
    val gelarS1FocusRequester = FocusRequester()
    val gelarS2FocusRequester = FocusRequester()
    val gelarS3FocusRequester = FocusRequester()
    val daerahKepengurusanFocusRequester = FocusRequester()

    // State untuk menyimpan URI file yang dipilih
    var fileUri1 by remember { mutableStateOf<Uri?>(null) }
    var fileUri2 by remember { mutableStateOf<Uri?>(null) }
    var fileName1 by remember { mutableStateOf("") }
    var fileName2 by remember { mutableStateOf("") }

    // Launcher untuk memilih file
    val launcher1 =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                fileUri1 = it
                fileName1 = getFileName(context, it)
            }
        }

    val launcher2 =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                fileUri2 = it
                fileName2 = getFileName(context, it)
            }
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
                    text = "Registrasi Keanggotaan",
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
                FormDataAkun(
                    email = email,
                    onEmailChange = { email = it },
                    emailError = emailError,
                    createPassword = createPassword,
                    onCreatePasswordChange = { createPassword = it },
                    createPasswordError = createPasswordError,
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = { confirmPassword = it },
                    confirmPasswordError = confirmPasswordError,
                    emailFocusRequester = emailFocusRequester,
                    createPasswordFocusRequester = createPasswordFocusRequester,
                    confirmPasswordFocusRequester = confirmPasswordFocusRequester
                )

                Spacer(modifier = modifier.height(16.dp))

                FormDataDiri(
                    fullName = fullName,
                    onFullNameChange = { fullName = it },
                    nik = nik,
                    onNikChange = { nik = it },
                    tempatLahir = tempatLahir,
                    onTempatLahirChange = { tempatLahir = it },
                    tanggalLahir = tanggalLahir,
                    onTanggalLahirChange = { tanggalLahir = it },
                    jenisKelamin = jenisKelamin,
                    onJenisKelaminChange = { jenisKelamin = it },
                    alamat = alamat,
                    onAlamatChange = { alamat = it },
                    kabupatenKota = kabupatenKota,
                    onKabupatenKotaChange = { kabupatenKota = it },
                    kodePos = kodePos,
                    onKodePosChange = { kodePos = it },
                    nomorWA = nomorWA,
                    onNomorWAChange = { nomorWA = it },
                    gelarS1 = gelarS1,
                    onGelarS1Change = { gelarS1 = it },
                    gelarS2 = gelarS2,
                    onGelarS2Change = { gelarS2 = it },
                    gelarS3 = gelarS3,
                    onGelarS3Change = { gelarS3 = it },
                    daerahKepengurusan = daerahKepengurusan,
                    onDaerahKepengurusanChange = { daerahKepengurusan = it },
                    fullNameFocusRequester = fullNameFocusRequester,
                    nikFocusRequester = nikFocusRequester,
                    tempatLahirFocusRequester = tempatLahirFocusRequester,
                    tanggalLahirFocusRequester = tanggalLahirFocusRequester,
                    alamatFocusRequester = alamatFocusRequester,
                    kabupatenKotaFocusRequester = kabupatenKotaFocusRequester,
                    kodePosFocusRequester = kodePosFocusRequester,
                    nomorWAFocusRequester = nomorWAFocusRequester,
                    gelarS1FocusRequester = gelarS1FocusRequester,
                    gelarS2FocusRequester = gelarS2FocusRequester,
                    gelarS3FocusRequester = gelarS3FocusRequester,
                    daerahKepengurusanFocusRequester = daerahKepengurusanFocusRequester
                )



                Spacer(modifier = modifier.height(16.dp))
                // Button to select first file
                Button(onClick = { launcher1.launch(arrayOf("application/pdf")) }) {
                    Text("Pilih File PDF 1")
                }
                Text(text = "File 1: $fileName1")

                Spacer(modifier = Modifier.height(8.dp))

                // Button to select second file
                Button(onClick = { launcher2.launch(arrayOf("application/pdf")) }) {
                    Text("Pilih File PDF 2")
                }
                Text(text = "File 2: $fileName2")

                Spacer(modifier = Modifier.height(16.dp))
                ButtonCustom(
                    btnText = stringResource(R.string.register),
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

                            createPassword.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.password_harus_diisi),
                                    Toast.LENGTH_SHORT
                                ).show()
                                createPasswordFocusRequester.requestFocus()
                            }

                            confirmPassword.isEmpty() -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.konfirmasi_password_harus_diisi),
                                    Toast.LENGTH_SHORT
                                ).show()
                                confirmPasswordFocusRequester.requestFocus()
                            }

                            createPassword != confirmPassword -> {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.password_dan_konfirmasi_password_harus_sama),
                                    Toast.LENGTH_SHORT
                                ).show()
                                confirmPasswordFocusRequester.requestFocus()
                            }

                            emailError.isNotEmpty() -> {
                                Toast.makeText(context, emailError, Toast.LENGTH_SHORT).show()
                                emailFocusRequester.requestFocus()
                            }

                            createPasswordError.isNotEmpty() -> {
                                Toast.makeText(context, createPasswordError, Toast.LENGTH_SHORT)
                                    .show()
                                createPasswordFocusRequester.requestFocus()
                            }

                            confirmPasswordError.isNotEmpty() -> {
                                Toast.makeText(context, confirmPasswordError, Toast.LENGTH_SHORT)
                                    .show()
                                confirmPasswordFocusRequester.requestFocus()
                            }

                            else -> {
                                if (fileUri1 != null && fileUri2 != null) {
                                    val file1 = File(context.cacheDir, fileName1).apply {
                                        context.contentResolver.openInputStream(fileUri1!!)
                                            ?.use { input ->
                                                outputStream().use { output -> input.copyTo(output) }
                                            }
                                    }
                                    val file2 = File(context.cacheDir, fileName2).apply {
                                        context.contentResolver.openInputStream(fileUri2!!)
                                            ?.use { input ->
                                                outputStream().use { output -> input.copyTo(output) }
                                            }
                                    }
                                    viewModel.fetchRegister(
                                        member = RegistrasiKeanggotaan(
                                            fullName,
                                            email,
                                            confirmPassword
                                        ),
                                        pdfFiles = listOf(file1, file2)
                                    )
                                }
                                isLoading = true
                            }
                        }
                    }
                )
            }
        }
    }

    if (postSuccess) {
        isLoading = false
        Toast.makeText(context, stringResource(R.string.silahkan_cek_email_anda), Toast.LENGTH_SHORT).show()
    }

    if (postError) {
        isLoading = false
        Toast.makeText(context, stringResource(R.string.gagal_mengirim_email), Toast.LENGTH_SHORT).show()
        viewModel.resetError()
    }

}

private fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        fileName = cursor.getString(nameIndex)
    }
    return fileName
}