package org.ucsmconecta.components.body

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ucsmconecta.components.card.cardCarreras
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.logoApp
import ucsmconecta.composeapp.generated.resources.AlfaSlabOne
import ucsmconecta.composeapp.generated.resources.ReadexProBold
import ucsmconecta.composeapp.generated.resources.ucsmIngSistemas
import ucsmconecta.composeapp.generated.resources.ucsmIngIndustrial

@Composable
fun HeaderBody() {
    val logoApp = painterResource(Res.drawable.logoApp)
    val alfaSlabOne = FontFamily(
        Font(Res.font.AlfaSlabOne)
    )
    Box(
        modifier = Modifier
            .height(164.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.Transparent)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = logoApp,
                contentDescription = "Logo UCSM",
                modifier = Modifier
                    .fillMaxWidth(0.35f) // 40% del ancho de la pantalla
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit // Ajusta la imagen al espacio disponible
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "UcsmConecta",
                    fontSize = 26.sp,
                    color = Color.White,
                    fontFamily = alfaSlabOne
                )
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .fillMaxWidth(0.8f)
                        .background(color = Color.White)
                )
                Text(
                    text = "Control de Asistencia",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = alfaSlabOne
                )
            }

        }
    }
}

@Composable
fun CuerpoCarreras(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onCardSelected: () -> Unit
) {
    // Lista de imágenes de las carreras
    val listImages = listOf(
        painterResource(Res.drawable.ucsmIngSistemas),
        painterResource(Res.drawable.ucsmIngIndustrial),
    )
    val READEX_PRO_BOLD = FontFamily(
        Font(Res.font.ReadexProBold)
    ) // Fuente Readex Pro Bold
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SubtitleBody(subtitle = "PARTICIPANTES", fontFamily = READEX_PRO_BOLD, fontSize = 24.sp) // Título de la sección
        // LazyColumn para mostrar las tarjetas de carreras con scroll
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(6.dp), // Espacio alrededor de los elementos
            verticalArrangement = Arrangement.spacedBy(15.dp), // Espacio entre los elementos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(listImages.size) { i ->
                cardCarreras(
                    // Se usa when para asignar el nombre de la carrera según el índice
                    carrera = when (i) {
                        0 -> "Escuela Profesional de Ingeniería de Sistemas"
                        1 -> "Escuela Profesional de Ingeniería Industrial"
                        else -> "Carrera Desconocida"
                    },
                    image = listImages[i],
                    onclick = {
                        scope.launch { // Acción al hacer clic en la tarjeta
                            onCardSelected()  // notificar para animar
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun BodyParticipantes(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onCardSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .padding(10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderBody()
        CuerpoCarreras(
            snackbarHostState,
            scope,
            onCardSelected)
    }
}