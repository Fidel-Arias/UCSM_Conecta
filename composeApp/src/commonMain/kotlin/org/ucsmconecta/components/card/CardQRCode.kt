package org.ucsmconecta.components.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

@Composable
expect fun CardQRCode(
    qrCode: String,
    modifier: Modifier = Modifier,
    righteousFont: FontFamily
)