package org.ucsmconecta.components.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.components.card.CardPresentations
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.ponenteGerman

@Composable
fun PresentationsInterface(
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
                )
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardPresentations(
                modifier = Modifier.padding(10.dp),
                title = "Desafíos y oportunidades de la Inteligencia Artificial en el Perú",
                ponente = "Ing. Walter Pinedo",
                lugar = "Auditorio William Morris",
                startHour = "08:00",
                endHour = "10:45",
                date = "28/10/2025",
                font = FontFamily(Font(Res.font.Righteous_Regular)),
                image = painterResource(Res.drawable.ponenteGerman)
            )
            CardPresentations(
                modifier = Modifier.padding(10.dp),
                title = "Desafíos y oportunidades de la Inteligencia Artificial en el Perú",
                ponente = "Ing. Walter Pinedo",
                lugar = "Auditorio William Morris",
                startHour = "09:30",
                endHour = "12:45",
                date = "28/10/2025",
                font = FontFamily(Font(Res.font.Righteous_Regular)),
                image = painterResource(Res.drawable.ponenteGerman)
            )
            CardPresentations(
                modifier = Modifier.padding(10.dp),
                title = "Desafíos y oportunidades de la Inteligencia Artificial en el Perú",
                ponente = "Ing. Walter Pinedo",
                lugar = "Auditorio William Morris",
                startHour = "10:30",
                endHour = "14:45",
                date = "28/10/2025",
                font = FontFamily(Font(Res.font.Righteous_Regular)),
                image = painterResource(Res.drawable.ponenteGerman)
            )
            CardPresentations(
                modifier = Modifier.padding(10.dp),
                title = "Desafíos y oportunidades de la Inteligencia Artificial en el Perú",
                ponente = "Ing. Walter Pinedo",
                lugar = "Auditorio William Morris",
                startHour = "11:30",
                endHour = "15:45",
                date = "28/10/2025",
                font = FontFamily(Font(Res.font.Righteous_Regular)),
                image = painterResource(Res.drawable.ponenteGerman)
            )
        }
    }
}