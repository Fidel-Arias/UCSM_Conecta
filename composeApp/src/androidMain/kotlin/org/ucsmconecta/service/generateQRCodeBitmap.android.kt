package org.ucsmconecta.service

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.EncodeHintType

actual fun generateQRCodeBitmap(content: String, size: Int): ImageBitmap {
    val qrCodeWriter = QRCodeWriter()
    val hints = mapOf(
        EncodeHintType.MARGIN to 0 // sin margen alrededor
    )
    val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints)
    val bitmap = createBitmap(size, size)

    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap[x, y] = if (bitMatrix.get(x, y)) -0x1000000 else -0x1
        }
    }

    return bitmap.asImageBitmap()
}