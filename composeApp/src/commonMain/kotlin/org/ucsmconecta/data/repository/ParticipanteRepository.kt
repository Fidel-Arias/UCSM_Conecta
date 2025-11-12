package org.ucsmconecta.data.repository

import org.ucsmconecta.data.model.asistencia.DataResponseContarAsistencias
import org.ucsmconecta.data.model.bloque.BloqueResponse
import org.ucsmconecta.data.model.participante.ParticipanteResponse
import org.ucsmconecta.data.network.ApiService

class ParticipanteRepository(
    private val api: ApiService
) {

    suspend fun obtenerDatosParticipante(id: Long): Result<ParticipanteResponse> {
        return api.obtenerDatosParticipante(id)
    }

    suspend fun obtenerBloques(): Result<List<BloqueResponse>> {
        return api.obtenerBloques()
    }

    suspend fun obtenerFechaServidor(): String {
        return api.obtenerFechaServidor()
    }

    suspend fun obtenerCantidadAsistencias(numDocumento: String, congresoCodigo: String): Result<DataResponseContarAsistencias> {
        return api.obtenerCantidadAsistencias(
            numDocumento = numDocumento,
            congresoCod = congresoCodigo
        )
    }
}