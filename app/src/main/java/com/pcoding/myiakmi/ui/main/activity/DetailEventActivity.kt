package com.pcoding.myiakmi.ui.main.activity

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.ui.components.PosterView
import com.pcoding.myiakmi.ui.components.cards.DataRegistrasiCard
import com.pcoding.myiakmi.ui.theme.MyIAKMITheme
import com.pcoding.myiakmi.utils.SessionManager
import com.pcoding.myiakmi.utils.generateQRCodeBitmap
import com.pcoding.myiakmi.utils.showCamera
import com.pcoding.myiakmi.viewmodel.EventViewModel
import com.pcoding.myiakmi.viewmodel.RegistrasiKehadiranViewModel

class DetailEventActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyIAKMITheme {
                val eventId = intent.getStringExtra("event_id")
                val eventName = intent.getStringExtra("event_name")
                val eventLocation = intent.getStringExtra("event_location")
                val eventTime = intent.getStringExtra("event_time")
                DetailEventScreen(eventId, eventName, eventLocation, eventTime)
            }
        }
    }
}

@Composable
fun DetailEventScreen(
    eventId: String?,
    eventName: String?,
    eventLocation: String?,
    eventTime: String?,
    viewModel: EventViewModel = viewModel(),
    viewModelKehadiran: RegistrasiKehadiranViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userData = SessionManager.getUserData(context)
    val detail by viewModel.detailMember.collectAsState()
    val postSuccess by viewModelKehadiran.postSuccess.collectAsState()
    val postError by viewModelKehadiran.postError.collectAsState()
    val action = context.getString(R.string.presensi)
    val isError by viewModel.isError.collectAsState()
    val isLoadingScreen by viewModel.isLoading.collectAsState()
    var loadingQrResult by remember { mutableStateOf(false) }


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
                    context.getString(R.string.camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    val qrValue = "${userData.userid}/$eventId"
    val qrCodeBitmap: Bitmap? = generateQRCodeBitmap(qrValue, size = 500)

    LaunchedEffect(qrResult) {
        qrResult?.let { result ->
            if (result.contents == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.pembatalan_scan),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                loadingQrResult = true
                val qrCode = "${userData.userid}/${result.contents}"
                viewModelKehadiran.postRegistrasiKehadiran(
                    qrCode,
                    userData.userid.toString(),
                    "presensi"
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModelKehadiran.resetAksi()
        viewModel.reqDetailEvent(
            userData.userid.toString(),
            userData.member.toString(),
            eventId.toString()
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            IconButton(
                onClick = { (context as? Activity)?.finish() },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = colorResource(R.color.main_color)
                )
            }
            Text(
                text = stringResource(R.string.detail_event),
                modifier = modifier.align(Alignment.Center),
                color = colorResource(R.color.main_color),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                textAlign = TextAlign.Center
            )
        }
        if (isLoadingScreen || loadingQrResult) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(color = colorResource(R.color.main_color))
            }
        } else if (isError.isNotEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_error),
                    contentDescription = stringResource(R.string.ic_peringatan),
                    modifier = Modifier.size(64.dp),
                    tint = colorResource(R.color.unselected_color)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.gagal_memuat_detail_acara),
                    color = colorResource(R.color.selected_color),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            viewModel.resetError()
                            viewModel.reqDetailEvent(
                                userData.userid.toString(),
                                userData.member.toString(),
                                eventId.toString()
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = colorResource(R.color.selected_color)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.muat_ulang),
                        color = colorResource(R.color.selected_color),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular))
                    )
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                detail?.flyer?.let { PosterView(it) }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = eventName!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = colorResource(R.color.main_color),
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = eventLocation!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = eventTime!!,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = colorResource(R.color.text_color1),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.img_aestetic),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        contentScale = ContentScale.FillWidth
                    )
                    qrCodeBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            context.getString(R.string.message_fiture),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                ) {
                    Text(
                        text = stringResource(R.string.daftar_sekarang),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = colorResource(R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                detail?.let {
                    DataRegistrasiCard(
                        it.jenisPeserta,
                        it.statusRegistrasi,
                        it.statusPembayaran,
                        it.member,
                        it.abstrak,
                        it.keterangan,
                        it.presensi
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            showCamera(
                                context.getString(R.string.promt_qr_admin),
                                barcodeLauncher
                            )
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                ) {
                    Text(
                        text = stringResource(R.string.scan_qr_presensi),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = colorResource(R.color.white),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        textAlign = TextAlign.Start,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (postError) {
        loadingQrResult = false
        Toast.makeText(context, stringResource(R.string.gagal_terkirim), Toast.LENGTH_SHORT).show()
        viewModelKehadiran.resetAksi()
    }

    if (postSuccess) {
        loadingQrResult = false
        Toast.makeText(context, stringResource(R.string.terkirim, action), Toast.LENGTH_SHORT)
            .show()
        viewModelKehadiran.resetAksi()
    }
}