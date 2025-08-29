package org.ucsmconecta.components.body

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.components.card.CardQRCode
import org.ucsmconecta.components.inputs.InputReadOnly
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.userman_icon

@Composable
private fun NameUser(name: String) {
    // This function can be used to display the user's name
    val imagePerfil = painterResource(Res.drawable.userman_icon)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePerfil,
            contentDescription = "Mi Perfil",
            modifier = Modifier
                .size(60.dp),
        )
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Text(
            text = name,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .wrapContentWidth()
        )
    }
}

@Composable
private fun BlockDataUser(
    value: Int,
    label: String,
    righteousFont: FontFamily,
) {
    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = Color.LightGray
                )
        ) {
            Text(
                text = value.toString(),
                fontSize = 38.sp,
                fontFamily = righteousFont,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = label,
            fontSize = 16.sp,
            fontFamily = righteousFont,
            modifier = Modifier
                .padding(top = 5.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun PerfilInterface(
    title: String,
    modifier: Modifier
) {
    val righteousFont = FontFamily(Font(Res.font.Righteous_Regular))
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Título fijo en la parte superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .zIndex(2f), // encima del contenido con scroll
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = righteousFont
            )
        }

        // Contenido scrollable debajo del título
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(color = Color.White)
                .fillMaxSize()
                .padding(
                    top = 56.dp, // deja espacio para el header fijo
                    bottom = 10.dp,
                    start = 30.dp,
                    end = 30.dp
                )
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // sin el título aquí porque ya está arriba
            NameUser("Fidel Arias Arias")

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BlockDataUser(value = 0, label = "Asistencias", righteousFont)
                BlockDataUser(value = 0, label = "Día", righteousFont)
            }

            Spacer(modifier = Modifier.height(18.dp))

            InputReadOnly(value = "72657497", label = "CÓDIGO", righteousFont)

            Spacer(modifier = Modifier.height(18.dp))

            InputReadOnly(value = "NO MATRICULADO", label = "ESTADO", righteousFont)

            Spacer(modifier = Modifier.height(18.dp))

            CardQRCode(
                qrCode = "72657497",
                modifier = Modifier.fillMaxWidth(),
                righteousFont = righteousFont
            )
        }
    }
}
