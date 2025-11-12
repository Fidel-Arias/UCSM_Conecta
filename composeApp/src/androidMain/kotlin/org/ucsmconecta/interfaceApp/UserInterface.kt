package org.ucsmconecta.interfaceApp

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.activities.LoginActivity
import org.ucsmconecta.components.body.PerfilInterface
import org.ucsmconecta.components.body.PresentationsInterface
import org.ucsmconecta.components.navbar.Navbar
import org.ucsmconecta.components.refresh.RefreshableContent
import org.ucsmconecta.data.network.ApiService
import org.ucsmconecta.data.network.createHttpClient
import org.ucsmconecta.data.repository.ParticipanteRepository
import org.ucsmconecta.infra.errors.UnauthorizedException
import org.ucsmconecta.util.TokenStorage
import org.ucsmconecta.util.backLogin
import org.ucsmconecta.util.getTokenStorage
import org.ucsmconecta.viewmodel.AsistenciaViewModel
import org.ucsmconecta.viewmodel.BloqueViewModel
import org.ucsmconecta.viewmodel.ParticipanteViewModel
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.campusUcsmManiana

suspend fun recargarDatos(
    participanteViewModel: ParticipanteViewModel,
    bloqueViewModel: BloqueViewModel,
    token: TokenStorage
) {
    val id = token.getId()
    participanteViewModel.cargarDatosColaborador(id)
    bloqueViewModel.cargarTodosLosBloquesPorDia()
}

@Composable
actual fun InterfazUserApp(tokenStorage: TokenStorage) {
    val imageUCSMManiana = painterResource(Res.drawable.campusUcsmManiana)
    var selectedItemNavbar by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val font = FontFamily(Font(Res.font.Righteous_Regular))
    val coroutineScope = rememberCoroutineScope()

    // Conexion con el cliente
    val client = createHttpClient()

    // Llamando a repository
    val repository = remember { ParticipanteRepository(ApiService(client, tokenStorage)) }

    // Cargando la vista del viewModel
    val participanteViewModel = viewModel { ParticipanteViewModel(repository) }
    val bloquesViewModel = viewModel { BloqueViewModel(repository) }
    val asistenciaViewModel = viewModel { AsistenciaViewModel(repository) }

    // Estado del ViewModel
    val participanteState by participanteViewModel.uiState.collectAsState()
    val bloqueState by bloquesViewModel.uiState.collectAsState()

    val diaActual by produceState<String?>(initialValue = null) {
        value = repository.obtenerFechaServidor()
    }

    var firstLoad by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        if(firstLoad) {
            val token = tokenStorage.getToken()
            if (token == null) {
                backLogin()
            } else {
                try {
                    recargarDatos(participanteViewModel, bloquesViewModel, tokenStorage)
                } catch (e: UnauthorizedException) {
                    // Token expirado
                    backLogin()
                } catch (e: Exception) {
                    // Otro error
                    println("Error al cargar datos: ${e.message}")
                }
            }
            firstLoad = false
        }
    }

    Scaffold(
        bottomBar = {
            Navbar(
                selectedItem = selectedItemNavbar,
                onItemSelected = { newItem ->
                    if (newItem == 3) {
                        // 🔹 Ejecutar logout
                        CoroutineScope(Dispatchers.IO).launch {
                            getTokenStorage().clear()
                        }
                        (context as? Activity)?.let { activity ->
                            activity.startActivity(Intent(activity, LoginActivity::class.java))
                            activity.finish()
                        }
                    } else {
                        selectedItemNavbar = newItem
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Bienvenido",
                color = Color.White,
                fontFamily = font,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .padding(vertical = 60.dp)
                    .align(Alignment.TopCenter),
            )
            Text(
                text = "Registra tu asistencia fácilmente",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .padding(vertical = 100.dp)
                    .align(Alignment.TopCenter),
            )
            // Imagen de fondo en la parte superior
            Image(
                painter = imageUCSMManiana,
                contentDescription = "Campus UCSM",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.Crop
            )
            // Overlay negro con opacidad
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // Ajusta la opacidad aquí
            )

            // Contenido del cuerpo encima de la imagen, con esquinas redondeadas
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(top = 180.dp), // Este valor se ajusta al tamño de la imagen
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(
                    topStart = 40.dp,
                    topEnd = 40.dp
                ),
                color = Color.White
            ) {
                RefreshableContent(
                    selectedItem = selectedItemNavbar,
                    onRefresh = {
                        coroutineScope.launch {
                            recargarDatos(
                                participanteViewModel = participanteViewModel,
                                bloqueViewModel = bloquesViewModel,
                                token = tokenStorage
                            )
                        }
                    }
                ) {
                    when (selectedItemNavbar) {
                        0 ->
                            PerfilInterface(
                                title = "PERFIL",
                                modifier = Modifier.fillMaxSize(),
                                participanteState = participanteState,
                                diaActual = diaActual,
                                asistenciaViewModel = asistenciaViewModel
                            )
                        1 ->
                            PresentationsInterface(
                                title = "PONENCIAS",
                                modifier = Modifier.fillMaxSize(),
                                bloqueState = bloqueState
                            )
//                    2 ->
//                        CommentsInterface(
//                            title = "COMENTARIOS",
//                            modifier = Modifier.fillMaxSize()
//                        )
                    }
                }
            }
        }
    }
}