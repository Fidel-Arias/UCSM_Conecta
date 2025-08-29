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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.ucsmconecta.components.card.CardComment
import org.ucsmconecta.components.inputs.CommentTextField
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.userman_icon

@Composable
fun CommentsInterface(
    title: String,
    modifier: Modifier
) {
    val righteousFont = FontFamily(Font(Res.font.Righteous_Regular))
    val scrollState = rememberScrollState()
    var commentText by remember { mutableStateOf("") }

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
                .background(color = Color.White)
                .fillMaxSize()
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(
                        top = 56.dp, // deja espacio para el header fijo
                        bottom = 50.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(6) {
                    CardComment(
                        fotoPerfil = painterResource(Res.drawable.userman_icon),
                        nameUser = "Fidel Arias Arias",
                        date = "17/07/2025",
                        hour = "09:30",
                        comment = "Why can’t people just enjoy the Super Bowl! Sports events need so much security nowadays"
                    )
                }
            }
        }
        CommentTextField(
            value = commentText,
            onValueChange = { commentText = it },
            onSendClick = {
                // Acción al enviar comentario
                println("Comentario enviado: $commentText")
                commentText = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .zIndex(3f) // Asegura que esté por encima de todo
        )
    }
}