package org.ucsmconecta.data.repository

import org.ucsmconecta.data.model.bloque.BloqueResponse
import org.ucsmconecta.data.model.dia.TimeResponse
import org.ucsmconecta.data.model.login.LoginRequest
import org.ucsmconecta.data.model.login.LoginResponse
import org.ucsmconecta.data.model.participante.ParticipanteResponse
import org.ucsmconecta.data.network.ApiService
import org.ucsmconecta.util.TokenStorage

class AuthRepository(
    private val api: ApiService,
) {
    suspend fun login(numDocumento: String): Result<LoginResponse> {
        return api.login(LoginRequest(numDocumento))
    }
}