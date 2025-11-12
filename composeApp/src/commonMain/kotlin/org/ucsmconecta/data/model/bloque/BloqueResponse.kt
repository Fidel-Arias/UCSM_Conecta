package org.ucsmconecta.data.model.bloque

import kotlinx.serialization.Serializable
import org.ucsmconecta.data.model.dia.DiaResponse
import org.ucsmconecta.data.model.ponencia.PonenciaResponse
import org.ucsmconecta.data.model.ubicacion.UbicacionResponse

@Serializable
data class BloqueResponse(
    val id: Long,
    val horaInicial: String,
    val horaFinal: String,
    val dia: DiaResponse,
    val ubicacion: UbicacionResponse,
    val ponencia: PonenciaResponse
)
