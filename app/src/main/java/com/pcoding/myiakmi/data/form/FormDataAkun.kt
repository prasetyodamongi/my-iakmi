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
fun FormDataAkun(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String,
    createPassword: String,
    onCreatePasswordChange: (String) -> Unit,
    createPasswordError: String,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    confirmPasswordError: String,
    emailFocusRequester: FocusRequester,
    createPasswordFocusRequester: FocusRequester,
    confirmPasswordFocusRequester: FocusRequester
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.data_akun),
            color = colorResource(R.color.main_color),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold))
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email Input
        TextInput(
            inputType = InputType.Email,
            value = email,
            onValueChange = onEmailChange,
            focusRequester = emailFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                createPasswordFocusRequester.requestFocus()
            }),
            onError = { emailError }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Create Password Input
        TextInput(
            inputType = InputType.Password,
            value = createPassword,
            onValueChange = onCreatePasswordChange,
            focusRequester = createPasswordFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                confirmPasswordFocusRequester.requestFocus()
            }),
            isCreatingPassword = true,
            onError = { createPasswordError }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Input
        TextInput(
            inputType = InputType.Password,
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            focusRequester = confirmPasswordFocusRequester,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                // Close keyboard when done
            }),
            onError = { confirmPasswordError }
        )
    }
}