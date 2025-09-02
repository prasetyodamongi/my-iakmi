package com.pcoding.myiakmi.ui.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.components.InputType
import com.pcoding.myiakmi.ui.components.TextInput
import com.pcoding.myiakmi.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pcoding.myiakmi.utils.SessionManager

@Composable
fun DialogResetPassword(
    beforeLogin: Boolean,
    onDismiss: () -> Unit,
) {
    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    val emailFocusRequester = FocusRequester()

    val resetPassSuccess by viewModel.resetPassSuccess.collectAsState()
    val resetPassError by viewModel.resetPassError.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFECF0F1)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.password_reset),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.main_color),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.reset_pass_message),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.email),
                    color = colorResource(R.color.main_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextInput(
                    inputType = InputType.Email,
                    value = email,
                    onValueChange = { email = it },
                    focusRequester = emailFocusRequester,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    onError = { emailError = it }
                )
                if (beforeLogin) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.status_anggota),
                        color = colorResource(R.color.main_color),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption == "member",
                                onClick = { selectedOption = "member" },
                                modifier = Modifier.padding(8.dp),
                                colors = RadioButtonDefaults.colors(selectedColor = colorResource(R.color.main_color))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.anggota_iakmi),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular))
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption == "nonmember",
                                onClick = { selectedOption = "nonmember" },
                                modifier = Modifier.padding(8.dp),
                                colors = RadioButtonDefaults.colors(selectedColor = colorResource(R.color.main_color))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.bukan_anggota_iakmi),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular))
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(55.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color))
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.roboto_medium))
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            if (email.isEmpty()) {
                                Toast.makeText(context, context.getString(R.string.email_harus_diisi), Toast.LENGTH_SHORT).show()
                                emailFocusRequester.requestFocus()
                            } else {
                                if (beforeLogin) {
                                    if (selectedOption == null) {
                                        Toast.makeText(context,
                                            context.getString(R.string.harus_memilih_status_anggota), Toast.LENGTH_SHORT).show()
                                    } else {
                                        isLoading = true
                                        viewModel.resetPassword(email, selectedOption!!)
                                    }
                                } else {
                                    isLoading = true
                                    if (userData.member == 1) {
                                        viewModel.resetPassword(email, "member")
                                    } else {
                                        viewModel.resetPassword(email, "nonmember")
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(55.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                    ) {
                        if (isLoading) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(24.dp),
                                    color = Color.White
                                )
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.send),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.roboto_medium))
                            )
                        }
                    }
                }
            }
        }
    }

    if (resetPassSuccess) {
        isLoading = false
        Toast.makeText(context, stringResource(R.string.silahkan_cek_email_anda), Toast.LENGTH_SHORT).show()
        onDismiss()
    }

    if (resetPassError) {
        isLoading = false
        Toast.makeText(context, stringResource(R.string.gagal_mengirim_email), Toast.LENGTH_SHORT).show()
        viewModel.resetErrorResetPass()
    }
}