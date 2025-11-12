package org.ucsmconecta.data.model.comentario

import kotlinx.serialization.Serializable
import org.ucsmconecta.data.model.participante.ParticipanteResponse

@Serializable
data class ComentarioResponse(
    val hora: String,
    val fecha: String,
    val participante: ParticipanteResponse,
    val texto: String
)
