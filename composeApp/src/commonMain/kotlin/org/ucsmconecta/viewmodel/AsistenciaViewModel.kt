package org.ucsmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.data.repository.ParticipanteRepository

class AsistenciaViewModel(
    private val repository: ParticipanteRepository
) : ViewModel() {

    private val _cantidadAsistencias = MutableStateFlow<Int?>(null)
    val cantidadAsistencias = _cantidadAsistencias.asStateFlow()

    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje = _errorMensaje.asStateFlow()

    fun obtenerCantidadAsistencias(numDocumento: String, congresoCod: String) {
        viewModelScope.launch {
            val resultado = repository.obtenerCantidadAsistencias(numDocumento, congresoCod)
            resultado.onSuccess { data ->
                _cantidadAsistencias.value = data.totalAsistencias
                _errorMensaje.value = null
            }.onFailure { error ->
                _cantidadAsistencias.value = null
                _errorMensaje.value = error.message
            }
        }
    }
}

