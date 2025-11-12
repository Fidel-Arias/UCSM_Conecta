package org.ucsmconecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ucsmconecta.data.model.UiState.UiState
import org.ucsmconecta.data.model.login.LoginResponse
import org.ucsmconecta.data.repository.AuthRepository

class LoginViewModel(
    private val repository: AuthRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val uiState: StateFlow<UiState<LoginResponse>> = _uiState

    fun login(numDocumento: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.login(numDocumento)

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

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}