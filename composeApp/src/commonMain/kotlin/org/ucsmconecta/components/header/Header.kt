package org.ucsmconecta.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.ucsmLogoTitle

@Composable
fun header() {
    val painter = painterResource(Res.drawable.ucsmLogoTitle)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 80.dp)
            .background(color = Color.Black.copy(alpha = 0.8f))
    ) {
        Image(
            painter = painter,
            contentDescription = "Logo UCSM",
            modifier = Modifier
                .fillMaxWidth(0.8f) // 80% del ancho de la pantalla
                .aspectRatio(4f) // Mantiene la relación de aspecto 4:1
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    header()
}