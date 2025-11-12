package org.ucsmconecta.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val numDocumento: String,
)