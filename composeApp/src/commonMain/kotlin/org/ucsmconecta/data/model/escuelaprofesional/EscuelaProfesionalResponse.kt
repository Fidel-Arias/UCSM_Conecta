package org.ucsmconecta.data.model.escuelaprofesional

import kotlinx.serialization.Serializable

@Serializable
data class EscuelaProfesionalResponse(
    val id: Int,
    val nombre: String,
    val codigo: String
)