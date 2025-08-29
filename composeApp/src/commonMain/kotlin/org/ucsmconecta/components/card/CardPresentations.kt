package org.ucsmconecta.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.Righteous_Regular
import ucsmconecta.composeapp.generated.resources.ponenteGerman

@Composable
fun CardPresentations(
    modifier: Modifier,
    title: String,
    ponente: String,
    lugar: String,
    startHour: String,
    endHour: String,
    date: String,
    font: FontFamily,
    image: Painter
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp), // Separación entre cards
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                // Titulo de la Ponencia
                Text(
                    text = title,
                    fontFamily = font,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Nombre del ponente responsable
                Text(
                    text = ponente,
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Lugar de la ponencia
                Text(
                    text = "LUGAR: $lugar",
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Hora de la ponencia
                Text(
                    text = try {
                        // Para StartHour
                        val start = startHour.substring(0,2).toInt()
                        // Para EndHour
                        val end = endHour.substring(0,2).toInt()

                        val formattedStart = if (start < 12) "$startHour AM" else "$startHour PM"
                        val formattedEnd = if (end < 12) "$endHour AM" else "$endHour PM"

                        "HORA: $formattedStart - $formattedEnd"

                    } catch (e: Exception) {
                        "HORA: $startHour - $endHour"
                    },
                    fontSize = 10.sp,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Dia de la ponencia
                Text(
                    text = "DIA: $date",
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // IMAGEN DEL PONENTE (derecha)
            Image(
                painter = image,
                contentDescription = ponente,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun CardPresePrev() {
    CardPresentations(
        modifier = Modifier.padding(10.dp),
        title = "Desafíos y oportunidades de la Inteligencia Artificial en el Perú",
        ponente = "Ing. Walter Pinedo",
        lugar = "Auditorio William Morris",
        startHour = "09:30",
        endHour = "14:45",
        date = "28/10/2025",
        font = FontFamily(Font(Res.font.Righteous_Regular)),
        image = painterResource(Res.drawable.ponenteGerman)
    )
}