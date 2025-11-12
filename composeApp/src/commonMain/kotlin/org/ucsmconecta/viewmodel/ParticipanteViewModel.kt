package org.ucsmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.model.participante.ParticipanteResponse
import org.ucsmconecta.data.repository.ParticipanteRepository

class ParticipanteViewModel(
    private val repository: ParticipanteRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ParticipanteResponse>>(UiState.Idle)
    val uiState: StateFlow<UiState<ParticipanteResponse>> = _uiState

    fun cargarDatosColaborador(id: Long) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.obtenerDatosParticipante(id)

            result.fold(
                onSuccess = { response ->
                    _uiState.value = UiState.Success(response)
                },
                onFailure = { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error desconocido")
                }
            )
        }
    }
}