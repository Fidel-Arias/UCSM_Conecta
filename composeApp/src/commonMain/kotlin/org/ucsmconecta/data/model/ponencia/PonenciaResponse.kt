package org.ucsmconecta.data.model.ponencia

import kotlinx.serialization.Serializable
import org.ucsmconecta.data.model.ponente.PonenteResponse

@Serializable
data class PonenciaResponse(
    val id: Long,
    val nombre: String,
    val ponente: PonenteResponse,
)
