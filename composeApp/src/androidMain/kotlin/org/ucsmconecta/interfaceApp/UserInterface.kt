package org.ucsmconecta.interfaceApp

import android.app.Activity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ucsmconecta.components.body.CommentsInterface
import org.ucsmconecta.components.body.PerfilInterface
import org.ucsmconecta.components.body.PresentationsInterface
import org.ucsmconecta.components.navbar.Navbar
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.campusUcsmManiana

@Preview
@Composable
actual fun InterfazUserApp() {
    val imageUCSMManiana = painterResource(Res.drawable.campusUcsmManiana)
    var selectedItemNavbar by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val font = FontFamily(Font(Res.font.Righteous_Regular))

    Scaffold(
        bottomBar = {
            Navbar(
                selectedItem = selectedItemNavbar,
                onItemSelected = { newItem ->
                    if (newItem == 3) {
                        (context as? Activity)?.finish()
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
                when (selectedItemNavbar) {
                    0 ->
                        PerfilInterface(
                            title = "PERFIL",
                            modifier = Modifier.fillMaxSize()
                        )
                    1 ->
                        PresentationsInterface(
                            title = "PONENCIAS",
                            modifier = Modifier.fillMaxSize()
                        )
                    2 ->
                        CommentsInterface(
                            title = "COMENTARIOS",
                            modifier = Modifier.fillMaxSize()
                        )
                }
            }
        }
    }
}