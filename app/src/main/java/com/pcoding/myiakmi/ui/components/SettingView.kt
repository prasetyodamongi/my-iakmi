package com.pcoding.myiakmi.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pcoding.myiakmi.R

@Composable
fun SettingItem(txt1: String, txt2: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = txt1,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            textAlign = TextAlign.Start
        )
        Text(
            text = txt2,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun SettingItemClickable(txt1: String, txt2: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = txt1,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            textAlign = TextAlign.Start
        )
        Text(
            text = txt2,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = colorResource(R.color.text_color1),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            textAlign = TextAlign.Start
        )
    }
}