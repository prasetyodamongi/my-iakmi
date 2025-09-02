package com.pcoding.myiakmi.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pcoding.myiakmi.R

@Composable
fun TextInput(
    inputType: InputType,
    hint: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Int = inputType.icon,
    singleLine: Boolean = true,
    focusRequester: FocusRequester? = null,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    isCreatingPassword: Boolean = false,
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val finalHint = hint ?: if (inputType.hintResId != 0) context.getString(inputType.hintResId) else ""

    val currentVisualTransformation = when (inputType) {
        is InputType.Password -> if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        is InputType.PasswordLogin -> if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    val icVisibility = painterResource(id = R.drawable.ic_visibility)
    val icVisibilityOff = painterResource(id = R.drawable.ic_visibility_off)

    fun validateInput(newValue: String) {
        errorMessage = when (inputType) {
            is InputType.Email -> {
                if (newValue.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(newValue).matches()) {
                    context.getString(R.string.email_tidak_valid)
                } else {
                    ""
                }
            }
            is InputType.Password -> {
                if (newValue.length < 6) {
                    context.getString(R.string.password_minimal_6_karakter)
                } else if (isCreatingPassword &&
                    (!newValue.any { it.isUpperCase() } ||
                            !newValue.any { it.isLowerCase() } ||
                            !newValue.any { it.isDigit() } ||
                            !newValue.any { !it.isLetterOrDigit() })) {
                    context.getString(R.string.password_harus_mengandung_kombinasi_huruf_besar_huruf_kecil_angka_dan_karakter_spesial)
                } else {
                    ""
                }
            }
            else -> ""
        }
        onValueChange(newValue)
        onError(errorMessage)
    }

    TextField(
        value = value,
        onValueChange = { validateInput(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .border(
                width = 1.dp,
                color = colorResource(R.color.main_color),
                shape = RoundedCornerShape(15.dp)
            ),
        textStyle = TextStyle(fontSize = 16.sp),
        placeholder = { Text(text = finalHint, color = colorResource(R.color.main_color)) },
        leadingIcon = { Icon(painter = painterResource(icon), contentDescription = null, tint = colorResource(R.color.main_color)) },
        trailingIcon = {
            if (inputType is InputType.Password) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = if (isPasswordVisible) icVisibility else icVisibilityOff,
                        contentDescription = null,
                        tint = colorResource(R.color.main_color)
                    )
                }
            } else if (inputType is InputType.PasswordLogin) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = if (isPasswordVisible) icVisibility else icVisibilityOff,
                        contentDescription = null,
                        tint = colorResource(R.color.main_color)
                    )
                }
            }
        },
        visualTransformation = currentVisualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(R.color.text_color1),
            unfocusedTextColor = colorResource(R.color.text_color1),
            disabledTextColor = colorResource(R.color.text_color1),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = colorResource(R.color.main_color),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )

    // Tampilkan pesan error jika ada
    if (errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

sealed class InputType(
    val icon: Int,
    val hintResId: Int
) {
    data object Normal : InputType(
        icon = 0,
        hintResId = 0
    )

    data object User : InputType(
        icon = R.drawable.ic_person,
        hintResId = R.string.masukan_username_email
    )

    data object PasswordLogin : InputType(
        icon = R.drawable.ic_password,
        hintResId = R.string.masukan_password
    )

    data object Email : InputType(
        icon = R.drawable.ic_alternate_email,
        hintResId = R.string.masukan_email
    )

    data object Password : InputType(
        icon = R.drawable.ic_password,
        hintResId = R.string.masukan_password
    )
}
