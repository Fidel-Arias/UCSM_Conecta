package org.ucsmconecta.data.model.tipoparticipante

import kotlinx.serialization.Serializable

@Serializable
data class TipoParticipanteResponse(
    val id: Long,
    val descripcion: String,
)