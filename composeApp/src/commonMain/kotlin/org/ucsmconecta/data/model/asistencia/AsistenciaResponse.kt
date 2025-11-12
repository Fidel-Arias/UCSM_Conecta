package org.ucsmconecta.data.model.asistencia

import kotlinx.serialization.Serializable

@Serializable
data class DataResponseContarAsistencias(
    val totalAsistencias: Int
)

