package org.ucsmconecta.data.model.congreso

import kotlinx.serialization.Serializable

@Serializable
data class CongresoResponse(
    val id: Long,
    val nombre: String,
    val codigo: String
)