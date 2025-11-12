package org.ucsmconecta.components.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import org.ucsmconecta.components.icons.getIconError
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.model.bloque.BloqueResponse
import org.ucsmconecta.ui.theme.PrimaryColor
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.ponencias_icon

@Composable
fun PresentationsInterface(
    title: String,
    modifier: Modifier,
    bloqueState: UiState<List<BloqueResponse>>
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
            when(bloqueState) {
                is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
                is UiState.Success -> {
                    if (bloqueState.data.isEmpty()) {
                        Text("Ponencias no disponibles", color = Color.Gray)
                    } else {
                        bloqueState.data.forEach { bloque ->
                            val nombresPonente = bloque.ponencia.ponente.nombres
                            val apellidosPonente = bloque.ponencia.ponente.apellidos

                            CardPresentations(
                                modifier = Modifier.padding(10.dp),
                                title = bloque.ponencia.nombre,
                                ponente = "$nombresPonente $apellidosPonente",
                                lugar = bloque.ubicacion.nombre,
                                startHour = bloque.horaInicial,
                                endHour = bloque.horaFinal,
                                date = bloque.dia.fecha,
                                font = FontFamily(Font(Res.font.Righteous_Regular)),
                                image = painterResource(Res.drawable.ponencias_icon)
                            )
                        }
                    }

                }
                is UiState.Error ->  {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = getIconError(),
                            contentDescription = "Error",
                            tint = Color.Gray,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(bloqueState.message, color = Color.Gray)
                    }
                }
                else -> Unit
            }

        }
    }
}