package com.example.registrojugadorestic_tac_toe.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrojugadorestic_tac_toe.domain.usecase.DeleteJugadorUseCase
import com.example.registrojugadorestic_tac_toe.domain.usecase.ObserveJugadorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListJugadorViewModel @Inject constructor(
    private val observeJugadoresUseCase: ObserveJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListJugadorUiState(isLoading = true))
    val state: StateFlow<ListJugadorUiState> = _state.asStateFlow()

    init {
        onEvent(ListJugadorUiEvent.Load)
    }

    fun onEvent(event: ListJugadorUiEvent) {
        when (event) {
            is ListJugadorUiEvent.Load -> observe()
            is ListJugadorUiEvent.Delete -> onDelete(event.jugadorId)
            ListJugadorUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListJugadorUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.jugadorId) }
            is ListJugadorUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeJugadoresUseCase().collectLatest { list ->
                _state.update { it.copy(isLoading = false, jugadores = list, message = null) }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteJugadorUseCase(id)
            onEvent(ListJugadorUiEvent.ShowMessage("Jugador eliminado"))
        }
    }

    fun onNavigationHandled() {
        _state.update { it.copy(navigateToCreate = false, navigateToEditId = null) }
    }
}