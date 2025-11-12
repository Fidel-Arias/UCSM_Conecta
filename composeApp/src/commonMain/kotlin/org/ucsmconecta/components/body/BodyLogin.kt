package org.ucsmconecta.components.body

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.components.inputs.DocumentoTextField
import org.ucsmconecta.components.button.CustomButton
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.network.ApiService
import org.ucsmconecta.data.network.createHttpClient
import org.ucsmconecta.data.repository.AuthRepository
import org.ucsmconecta.ui.theme.ErrorColor
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.ReadexProBold
import org.ucsmconecta.ui.theme.PrimaryColor
import org.ucsmconecta.util.getTokenStorage
import org.ucsmconecta.viewmodel.LoginViewModel
import ucsmconecta.composeapp.generated.resources.logoJinis2025

@Composable
fun BodyLogin(onLoginSuccess: () -> Unit) {
    val READEX_PRO_BOLD = FontFamily(
        Font(Res.font.ReadexProBold)
    ) // Fuente Readex Pro Bold

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        HeaderBodyLogin()
        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el encabezado y el cuerpo
        SubtitleBody(
            subtitle = "XXXII Jornada Internacional de Ingeniería de Sistemas",
            fontFamily = READEX_PRO_BOLD,
            fontSize = 18.sp
        ) // Título de la sección
        Spacer(modifier = Modifier.height(20.dp))
        Login(
            readexProBold = READEX_PRO_BOLD,
            onLoginSuccess = { onLoginSuccess() },
        )
    }
}

@Composable
fun HeaderBodyLogin() {
    val logoJinis = painterResource(Res.drawable.logoJinis2025)
    Box(
        modifier = Modifier
            .height(164.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = logoJinis,
            contentDescription = "Logo JINIS",
            modifier = Modifier
                .fillMaxWidth(1f) // 90% del ancho de la pantalla
                .fillMaxHeight(),
            contentScale = ContentScale.Fit // Ajusta la imagen al espacio disponible
        )
    }
}

@Composable
fun Login(
    readexProBold: FontFamily,
    onLoginSuccess: () -> Unit,
) {
    // Variables para almacenar el estado de los campos de texto
    var numDocumento by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var pendingMessage by remember { mutableStateOf<String?>(null) }

    // Manejador de almacenamiento (Android/iOS)
    val tokenStorage = getTokenStorage()

    // Implementacion del cliente
    val client = createHttpClient()

    // ViewModel y repositorio
    val repository = remember { AuthRepository(ApiService(client, null)) }
    val viewModel = viewModel { LoginViewModel(repository) }

    // Estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // TextFields para el inicio de sesión
    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = PrimaryColor,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.White,
        unfocusedContainerColor = Color.White,
        cursorColor = PrimaryColor,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black
    )

    Column(
        modifier = Modifier
            .fillMaxHeight(0.60f)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp)) // <- recorta el contenedor con esquinas redondeadas
            .background(Color.Black.copy(0.5f))
            .padding(20.dp), // Espacio alrededor de los campos de texto
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "INICIAR SESIÓN",
            fontFamily = readexProBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            color = PrimaryColor
        )

        Spacer(modifier = Modifier.height(20.dp)) // Espacio entre el título y los campos de texto

        DocumentoTextField(
            value = numDocumento,
            onValueChange = { numDocumento = it },
            label = "Número de documento",
            textFieldColors = textFieldColors,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        )

        when (uiState) {
            is UiState.Idle -> {
                pendingMessage?.let { msg ->
                    Text(
                        text = msg,
                        fontSize = 14.sp,
                        color = ErrorColor
                    )
                    // limpiar el mensaje en la siguiente composición de forma segura:
                    LaunchedEffect(msg) {
                        // esperar 3 segundo para eliminar
                        delay(3000)
                        pendingMessage = null
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomButton(
                    onClick = {
                        if (numDocumento.isNotBlank()) {
                            viewModel.login(numDocumento)
                        } else {
                            pendingMessage = "Ingrese su número de documento" // sólo setea el estado
                        }
                    },
                    text = "Ingresar"
                )
            }

            is UiState.Loading -> {
                Spacer(modifier = Modifier.height(20.dp))
                CircularProgressIndicator(color = PrimaryColor)
            }

            is UiState.Success -> {
                val data = (uiState as UiState.Success).data
                val token = data.token
                val role = data.role
                val id = data.id

                // Guardar token y navegar
                LaunchedEffect(token) {
                    tokenStorage.saveToken(token, role, id)
                    onLoginSuccess()
                }
            }

            is UiState.Error -> {
                val message = (uiState as UiState.Error).message
                Text("$message", color = Color.Red)
                LaunchedEffect(message) {
                    delay(3000)
                    viewModel.resetState() // <-- debes agregar esta función en tu ViewModel
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(
                    onClick = {
                        viewModel.login(numDocumento)
                    },
                    text = "Reintentar"
                )
            }
        }
    }
}
