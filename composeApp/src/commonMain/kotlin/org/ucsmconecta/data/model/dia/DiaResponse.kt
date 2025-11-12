package org.ucsmconecta.data.model.dia

import kotlinx.serialization.Serializable

@Serializable
data class TimeResponse(val serverTime: String)

@Serializable
data class DiaResponse(
    val id: Long,
    val fecha: String,
)
