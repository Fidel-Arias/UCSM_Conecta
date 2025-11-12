package org.ucsmconecta.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.ucsmconecta.components.icons.getIconError
import org.ucsmconecta.ui.theme.PrimaryColor

@Composable
fun CardQRCode(
    qrUrl: String,
    modifier: Modifier = Modifier,
    righteousFont: FontFamily
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "CÓDIGO QR",
            fontSize = 20.sp,
            fontFamily = righteousFont,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .clickable { showDialog = true },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box( // 👈 Envuelve el contenido del QR para poder usar align()
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KamelImage(
                    resource = asyncPainterResource(qrUrl),
                    contentDescription = "QR Code",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentScale = ContentScale.Fit,
                    onLoading = {
                        CircularProgressIndicator(
                            color = PrimaryColor
                        )
                    },
                    onFailure = {
                        Icon(
                            imageVector = getIconError(),
                            contentDescription = "Error al cargar QR",
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                )
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                KamelImage(
                    resource = asyncPainterResource(qrUrl),
                    contentDescription = "QR Ampliado",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}