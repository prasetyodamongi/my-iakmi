package com.pcoding.myiakmi.ui.main.nav

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.components.dialogs.DialogAksiRegistrasiKehadiran
import com.pcoding.myiakmi.ui.components.dialogs.DialogNotifikasi
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.utils.getSavedLanguagePreference
import com.pcoding.myiakmi.utils.setLocale
import com.pcoding.myiakmi.utils.showCamera

@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    var showDialogNotifikasi by remember { mutableStateOf(false) }
    var showDialogAksi by remember { mutableStateOf(false) }
    var qrResult by remember { mutableStateOf<ScanIntentResult?>(null) }
    val barcodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        qrResult = result
    }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                showCamera(context.getString(R.string.promt_qr_admin), barcodeLauncher)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.camera_permission), Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    LaunchedEffect(qrResult) {
        qrResult?.let { result ->
            if (result.contents == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.pembatalan_scan), Toast.LENGTH_SHORT
                ).show()
            } else {
                showDialogAksi = true
            }
        }
    }

    Box(
        Modifier.fillMaxWidth()
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = stringResource(R.string.ic_desc_item1),
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedItem == 0) colorResource(R.color.selected_color) else colorResource(
                            R.color.unselected_color
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.title_item1),
                        color = if (selectedItem == 0) colorResource(R.color.selected_color) else colorResource(
                            R.color.unselected_color
                        ),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                },
                selected = selectedItem == 0,
                onClick = { onItemSelected(0) }
            )

            Spacer(modifier = Modifier.weight(1f))

            BottomNavigationItem(
                icon = {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.ic_desc_item2),
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedItem == 2) colorResource(R.color.selected_color) else colorResource(
                            R.color.unselected_color
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.title_item2),
                        color = if (selectedItem == 2) colorResource(R.color.selected_color) else colorResource(
                            R.color.unselected_color
                        ),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                },
                selected = selectedItem == 2,
                onClick = { onItemSelected(2) }
            )
        }

        FloatingActionButton(
            onClick = {
                if (userData.isadmin == 1) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        showCamera(context.getString(R.string.promt_qr_admin), barcodeLauncher)
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                } else {
                    showDialogNotifikasi = true
                }
            },
            containerColor = colorResource(R.color.main_color),
            shape = CircleShape,
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-20).dp),
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
        ) {
            Icon(
                painterResource(R.drawable.ic_qr_scanner),
                contentDescription = stringResource(R.string.ic_desc_item3),
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }

    if (showDialogNotifikasi) {
        DialogNotifikasi(
            title = stringResource(R.string.app_name),
            teks = stringResource(R.string.teks_notifikasi_qr_scanner)
        ) {
            showDialogNotifikasi = false
        }
    }

    if (showDialogAksi) {
        DialogAksiRegistrasiKehadiran(qrcode = qrResult!!.contents) {
            showDialogAksi = false
        }
    }
}
