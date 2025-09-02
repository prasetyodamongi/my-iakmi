package com.pcoding.myiakmi.data.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.components.InputType
import com.pcoding.myiakmi.ui.components.TextInput

@Composable
fun FormDataDiri(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    nik: String,
    onNikChange: (String) -> Unit,
    tempatLahir: String,
    onTempatLahirChange: (String) -> Unit,
    tanggalLahir: String,
    onTanggalLahirChange: (String) -> Unit,
    jenisKelamin: String,
    onJenisKelaminChange: (String) -> Unit,
    alamat: String,
    onAlamatChange: (String) -> Unit,
    kabupatenKota: String,
    onKabupatenKotaChange: (String) -> Unit,
    kodePos: String,
    onKodePosChange: (String) -> Unit,
    nomorWA: String,
    onNomorWAChange: (String) -> Unit,
    gelarS1: String,
    onGelarS1Change: (String) -> Unit,
    gelarS2: String,
    onGelarS2Change: (String) -> Unit,
    gelarS3: String,
    onGelarS3Change: (String) -> Unit,
    daerahKepengurusan: String,
    onDaerahKepengurusanChange: (String) -> Unit,
    fullNameFocusRequester: FocusRequester,
    nikFocusRequester: FocusRequester,
    tempatLahirFocusRequester: FocusRequester,
    tanggalLahirFocusRequester: FocusRequester,
    alamatFocusRequester: FocusRequester,
    kabupatenKotaFocusRequester: FocusRequester,
    kodePosFocusRequester: FocusRequester,
    nomorWAFocusRequester: FocusRequester,
    gelarS1FocusRequester: FocusRequester,
    gelarS2FocusRequester: FocusRequester,
    gelarS3FocusRequester: FocusRequester,
    daerahKepengurusanFocusRequester: FocusRequester
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Data Diri",
            color = colorResource(R.color.main_color),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold))
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nama Lengkap
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Nama Lengkap",
            value = fullName,
            onValueChange = { onFullNameChange(it) },
            icon = R.drawable.ic_person,
            focusRequester = fullNameFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                nikFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // NIK
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan NIK",
            value = nik,
            onValueChange = { onNikChange(it) },
            icon = R.drawable.ic_person,
            focusRequester = nikFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                tempatLahirFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Tempat Lahir
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Tempat Lahir",
            value = tempatLahir,
            onValueChange = { onTempatLahirChange(it) },
            icon = R.drawable.ic_person,
            focusRequester = tempatLahirFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                tanggalLahirFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Tanggal Lahir
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Tanggal Lahir",
            value = tanggalLahir,
            onValueChange = { onTanggalLahirChange(it) },
            icon = R.drawable.ic_calendar,
            focusRequester = tanggalLahirFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                alamatFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Jenis Kelamin
        DropdownMenuField(
            label = "Jenis Kelamin",
            selectedValue = jenisKelamin,
            onValueChange = { onJenisKelaminChange(it) },
            items = listOf("Laki-laki", "Perempuan"),
            focusRequester = nikFocusRequester
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Alamat
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Alamat",
            value = alamat,
            onValueChange = { onAlamatChange(it) },
            icon = R.drawable.ic_location,
            focusRequester = alamatFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                kabupatenKotaFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Kabupaten/Kota
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Kabupaten/Kota",
            value = kabupatenKota,
            onValueChange = { onKabupatenKotaChange(it) },
            icon = R.drawable.ic_location,
            focusRequester = kabupatenKotaFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                kodePosFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Kode Pos
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Kode Pos",
            value = kodePos,
            onValueChange = { onKodePosChange(it) },
            icon = R.drawable.ic_location,
            focusRequester = kodePosFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                nomorWAFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Nomor WA
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Nomor WA",
            value = nomorWA,
            onValueChange = { onNomorWAChange(it) },
            icon = R.drawable.ic_call,
            focusRequester = nomorWAFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                gelarS1FocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Gelar S1
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Gelar S1",
            value = gelarS1,
            onValueChange = { onGelarS1Change(it) },
            icon = R.drawable.ic_degree,
            focusRequester = gelarS1FocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                gelarS2FocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Gelar S2
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Gelar S2",
            value = gelarS2,
            onValueChange = { onGelarS2Change(it) },
            icon = R.drawable.ic_degree,
            focusRequester = gelarS2FocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                gelarS3FocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Gelar S3
        TextInput(
            inputType = InputType.Normal,
            hint = "Masukkan Gelar S3",
            value = gelarS3,
            onValueChange = { onGelarS3Change(it) },
            icon = R.drawable.ic_degree,
            focusRequester = gelarS3FocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                daerahKepengurusanFocusRequester.requestFocus()
            })
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Daerah Kepengurusan
        DropdownMenuField(
            label = "Daerah Kepengurusan",
            selectedValue = daerahKepengurusan,
            onValueChange = { onDaerahKepengurusanChange(it) },
            items = listOf("Daerah 1", "Daerah 2", "Daerah 3"), // Gantilah dengan daftar yang sesuai
            focusRequester = daerahKepengurusanFocusRequester
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    selectedValue: String,
    onValueChange: (String) -> Unit,
    items: List<String>,
    focusRequester: FocusRequester
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color.Gray)
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { expanded = !expanded }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = selectedValue.ifEmpty { "Pilih $label" })
                Spacer(modifier = Modifier.weight(1f))
                Icon(icon, contentDescription = null)
            }
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                androidx.compose.material.DropdownMenuItem(onClick = {
                    onValueChange(item)
                    expanded = false
                }) {
                    Text(item)
                }
            }
        }
    }
}