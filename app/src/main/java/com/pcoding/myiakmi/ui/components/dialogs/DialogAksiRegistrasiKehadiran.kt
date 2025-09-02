package com.pcoding.myiakmi.ui.components.dialogs

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.viewmodel.RegistrasiKehadiranViewModel

@Composable
fun DialogAksiRegistrasiKehadiran(
    qrcode: String,
    viewModel: RegistrasiKehadiranViewModel = viewModel(),
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    val postSuccess by viewModel.postSuccess.collectAsState()
    val postError by viewModel.postError.collectAsState()
    var loading1 by remember { mutableStateOf(false) }
    var loading2 by remember { mutableStateOf(false) }
    var action by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.resetAksi()
    }

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
                    text = stringResource(R.string.title_dialog_aksi),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.main_color),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            loading1 = true
                            action = context.getString(R.string.registrasi_ulang)
                            viewModel.postRegistrasiKehadiran(
                                qrcode,
                                userData.userid.toString(),
                                "daftarulang"
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(1.dp, colorResource(R.color.main_color))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading1) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = colorResource(R.color.main_color)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.registrasi_kembali),
                                color = colorResource(R.color.main_color),
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            loading2 = true
                            action = context.getString(R.string.presensi)
                            viewModel.postRegistrasiKehadiran(
                                qrcode,
                                userData.userid.toString(),
                                "presensi"
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(1.dp, colorResource(R.color.main_color))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading2) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = colorResource(R.color.main_color)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.presensi),
                                color = colorResource(R.color.main_color),
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                }
            }
        }
    }

    if (postError) {
        loading1 = false
        loading2 = false
        Toast.makeText(context, stringResource(R.string.gagal_terkirim), Toast.LENGTH_SHORT).show()
        viewModel.resetAksi()
    }

    if (postSuccess) {
        loading1 = false
        loading2 = false
        Toast.makeText(context, stringResource(R.string.terkirim, action), Toast.LENGTH_SHORT).show()
        onDismiss()
        viewModel.resetAksi()
    }
}
