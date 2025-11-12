package org.ucsmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.model.bloque.BloqueResponse
import org.ucsmconecta.data.repository.ParticipanteRepository

class BloqueViewModel(
    private val repository: ParticipanteRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<BloqueResponse>>>(UiState.Idle)
    val uiState: StateFlow<UiState<List<BloqueResponse>>> = _uiState

    fun cargarTodosLosBloquesPorDia() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.obtenerBloques()

            result.fold(
                onSuccess = { response ->
                    if (response.isEmpty()) {
                        _uiState.value = UiState.Error("No hay bloques disponibles")
                    } else {
                        _uiState.value = UiState.Success(response)
                    }
                },
                onFailure = { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error desconocido")
                }
            )
        }
    }
}