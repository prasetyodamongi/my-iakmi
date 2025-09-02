package com.pcoding.myiakmi.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun DialogKonfirmasi(
    teks: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
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
                    text = stringResource(R.string.confirmation),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.main_color),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = teks,
                    color = colorResource(R.color.text_color1),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular))
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color))
                    ) {
                        Text(text = stringResource(R.string.cancel), color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { onConfirm() },
                        modifier = Modifier.weight(0.5f),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.main_color)),
                    ) {
                        Text(text = stringResource(R.string.confirm), color = Color.White)
                    }
                }
            }
        }
    }
}