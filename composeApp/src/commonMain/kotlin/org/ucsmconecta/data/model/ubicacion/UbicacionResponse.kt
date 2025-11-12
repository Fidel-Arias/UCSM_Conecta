package org.ucsmconecta.data.model.ubicacion

import kotlinx.serialization.Serializable

@Serializable
data class UbicacionResponse(
    val id: Long,
    val nombre: String
)
