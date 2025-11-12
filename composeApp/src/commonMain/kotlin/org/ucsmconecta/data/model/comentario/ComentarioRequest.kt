package org.ucsmconecta.data.model.comentario

import kotlinx.serialization.Serializable

@Serializable
data class ComentarioRequest(
    val texto: String,
    val participanteId: Long
)
