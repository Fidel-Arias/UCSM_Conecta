package org.ucsmconecta.data.model.participante

import kotlinx.serialization.Serializable
import org.ucsmconecta.data.model.congreso.CongresoResponse
import org.ucsmconecta.data.model.escuelaprofesional.EscuelaProfesionalResponse
import org.ucsmconecta.data.model.tipoparticipante.TipoParticipanteResponse

@Serializable
data class ParticipanteResponse(
    val nombres: String,
    val apMaterno: String,
    val apPaterno: String,
    val congreso: CongresoResponse,
    val escuelaProfesional: EscuelaProfesionalResponse,
    val estado: String,
    val numDocumento: String,
    val tipoParticipante: TipoParticipanteResponse,
    val qrCode: String? = null
)