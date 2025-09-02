package com.pcoding.myiakmi.ui.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pcoding.myiakmi.R
import com.pcoding.myiakmi.viewmodel.EventViewModel

@Composable
fun DataRegistrasiCard(
    jenisPeserta: String?,
    statusRegistrasi: String?,
    statusPembayaran: String?,
    member: String?,
    abstrak: String?,
    keterangan: String?,
    presensi: String?,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, colorResource(R.color.main_color)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.data_registrasi),
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.main_color),
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                textAlign = TextAlign.Center,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(R.string.jenis_peserta),
                    modifier = Modifier.weight(1.5f),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = jenisPeserta ?: "Tidak tersedia",
                    modifier = Modifier.weight(2f),
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    textAlign = TextAlign.Start,
                )
            }
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp))

            DataItem(stringResource(R.string.status_registrasi), statusRegistrasi)
            DataItem(stringResource(R.string.status_pembayaran), statusPembayaran)
            DataItem(stringResource(R.string.status_keanggotaan), member)
            DataItem(stringResource(R.string.status_abstrak), abstrak)
            DataItem(stringResource(R.string.keterangan), keterangan)
            DataItem(stringResource(R.string.presensi), presensi)
        }
    }
}

@Composable
fun DataItem(teks1: String, teks2: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = teks1,
            modifier = Modifier.weight(1.5f),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            textAlign = TextAlign.Start,
        )

        Text(
            text = teks2 ?: "Tidak tersedia", // Ganti dengan default value jika teks2 null
            modifier = Modifier.weight(2f),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            textAlign = TextAlign.Start,
        )
    }
}
