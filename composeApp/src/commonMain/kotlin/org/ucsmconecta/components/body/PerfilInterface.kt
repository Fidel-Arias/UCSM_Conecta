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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import org.ucsmconecta.components.icons.getIconError
import org.ucsmconecta.components.inputs.InputReadOnly
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.model.participante.ParticipanteResponse
import org.ucsmconecta.ui.theme.ErrorColor
import org.ucsmconecta.ui.theme.PrimaryColor
import org.ucsmconecta.viewmodel.AsistenciaViewModel
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.userman_icon

@Composable
private fun NameUser(name: String) {
    // This function can be used to display the user's name
    val imagePerfil = painterResource(Res.drawable.userman_icon)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePerfil,
            contentDescription = "Mi Perfil",
            modifier = Modifier.size(60.dp),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = name,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            softWrap = true
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

private fun getBaseUrl(): String {
    return "http://vps-5440778-x.dattaweb.com:8080"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilInterface(
    title: String,
    modifier: Modifier,
    participanteState: UiState<ParticipanteResponse>,
    diaActual: String?,
    asistenciaViewModel: AsistenciaViewModel
) {
    val righteousFont = FontFamily(Font(Res.font.Righteous_Regular))
    val scrollState = rememberScrollState()

    val totalAsistencias by asistenciaViewModel.cantidadAsistencias.collectAsState()
    val errorAsistencia by asistenciaViewModel.errorMensaje.collectAsState()

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
            when(participanteState) {
                is UiState.Loading -> CircularProgressIndicator(color = PrimaryColor)
                is UiState.Success -> {
                    val participante = participanteState.data
                    val nommbre = participante.nombres.split(" ")[0]
                    val apPaterno = participante.apPaterno
                    val apMaterno = participante.apMaterno

                    LaunchedEffect(participante.numDocumento) {
                        asistenciaViewModel.obtenerCantidadAsistencias(
                            numDocumento = participante.numDocumento,
                            congresoCod = participante.congreso.codigo
                        )
                    }

                    println("ASISTENCIA: $totalAsistencias")

                    NameUser("$nommbre $apPaterno $apMaterno")

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when {
                            totalAsistencias != null -> {
                                BlockDataUser(
                                    value = totalAsistencias ?: 0,
                                    label = "Asistencias",
                                    righteousFont = righteousFont
                                )
                            }
                            errorAsistencia != null -> {
                                println("ERROR ASISTENCIA: $errorAsistencia")
                                BlockDataUser(
                                    value = 0,
                                    label = "Error",
                                    righteousFont = righteousFont
                                )
                            }
                            else -> {
                                CircularProgressIndicator(color = Color.Gray)
                            }
                        }
                        when {
                            diaActual != null -> {
                                BlockDataUser(
                                    value = diaActual.split("-")[2].toInt(),
                                    label = "Día", righteousFont
                                )
                            }
                            else -> {
                                BlockDataUser(
                                    value = 0,
                                    label = "Error", righteousFont
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    InputReadOnly(value = participante.numDocumento, label = "CÓDIGO", righteousFont)

                    Spacer(modifier = Modifier.height(18.dp))

                    InputReadOnly(value = participante.estado, label = "ESTADO", righteousFont)

                    Spacer(modifier = Modifier.height(18.dp))

                    if (participante.qrCode != null) {
                        CardQRCode(
                            qrUrl = getBaseUrl() + participante.qrCode,
                            modifier = Modifier.fillMaxWidth(),
                            righteousFont = righteousFont
                        )
                    } else {
                        Text("QR no disponible", color = Color.Gray)
                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(
                            imageVector = getIconError(),
                            contentDescription = "Error"
                        )
                        Text("Error al cargar el participante", color = ErrorColor)
                    }
                }
                else -> Unit
            }
        }
    }
}
