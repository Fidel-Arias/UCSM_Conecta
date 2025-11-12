package org.ucsmconecta.components.inputs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.ucsmconecta.ui.theme.PrimaryColor
import ucsmconecta.composeapp.generated.resources.ClosedEyes_icon
import ucsmconecta.composeapp.generated.resources.Eye_icon
import ucsmconecta.composeapp.generated.resources.Res
import ucsmconecta.composeapp.generated.resources.arroba
import ucsmconecta.composeapp.generated.resources.send_icon

//@Composable
//fun EmailTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    textFieldColors: TextFieldColors,
//) {
//    val ArrobaIcon = painterResource(Res.drawable.arroba)
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        placeholder = {Text(label)},
//        singleLine = true,
//        leadingIcon = {
//            IconButton(
//                onClick = {}
//            ) {
//                Image(
//                    painter = ArrobaIcon,
//                    contentDescription = "Icono de correo",
//                    modifier = Modifier
//                        .size(30.dp)
//                )
//            }
//        },
//        shape = RoundedCornerShape(16.dp),
//        colors = textFieldColors,
//        modifier = Modifier
//            .fillMaxWidth()
//    )
//}

@Composable
fun DocumentoTextField(
    value: String,
    passwordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit,
    label: String,
    textFieldColors: TextFieldColors,
) {
    // Iconos para los campos de texto
    val EyeIcon = painterResource(Res.drawable.Eye_icon)
    val ClosedEyeIcon = painterResource(Res.drawable.ClosedEyes_icon)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {Text(label)},
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        leadingIcon = {
            val image = if (passwordVisible) EyeIcon else ClosedEyeIcon
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = onPasswordVisibilityChange) {
                Image(
                    painter = image,
                    contentDescription = description,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = textFieldColors,

        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CommentTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono de enviar
        Icon(
            painter = painterResource(Res.drawable.send_icon),
            contentDescription = "Enviar comentario",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onSendClick()
                },
            tint = PrimaryColor
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Caja de texto básica con línea inferior
        Column(
            modifier = Modifier.weight(1f)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = "Enviar un comentario...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            // Línea inferior gris
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                thickness = 1.dp,
                Color.LightGray
            )
        }
    }
}