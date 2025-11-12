package org.ucsmconecta.data.errors

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String
)
