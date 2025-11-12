package org.ucsmconecta.data.model.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val role: String,
    val id: Long
)