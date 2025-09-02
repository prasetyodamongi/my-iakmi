package com.pcoding.myiakmi.utils

import android.graphics.Bitmap
import android.graphics.Color
import androidx.activity.compose.ManagedActivityResultLauncher
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

fun showCamera(promt: String, barcodeLauncher: ManagedActivityResultLauncher<ScanOptions, ScanIntentResult>) {
    val options = ScanOptions().apply {
        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        setPrompt(promt)
        setCameraId(0)
        setBeepEnabled(false)
        setOrientationLocked(false)
    }
    barcodeLauncher.launch(options)
}

fun generateQRCodeBitmap(text: String, size: Int): Bitmap? {
    val hints = mapOf(
        EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L,
        EncodeHintType.MARGIN to 1
    )
    val writer = QRCodeWriter()
    return try {
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        bmp
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}