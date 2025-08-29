package org.ucsmconecta.service

import androidx.compose.ui.graphics.ImageBitmap

expect fun generateQRCodeBitmap(content: String, size: Int = 512): ImageBitmap
