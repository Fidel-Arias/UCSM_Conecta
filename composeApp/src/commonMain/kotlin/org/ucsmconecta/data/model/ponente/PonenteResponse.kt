package org.ucsmconecta.data.model.ponente

import kotlinx.serialization.Serializable

@Serializable
data class PonenteResponse(
    val id: Long,
    val nombres: String,
    val apellidos: String
)