package edu.ucne.RegistroJugadorAp2.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadorAp2.domain.usecase.DeleteJugadorUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecase.ObserveJugadorUseCase
import kotlinx.coroutines.flow.*
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
            is ListJugadorUiEvent.ShowMessage -> _state.update { it.copy(mensaje = event.message) }
        }
    }

    private fun observe() {
        viewModelScope.launch {
            observeJugadoresUseCase().collectLatest { list ->
                _state.update {
                    it.copy(
                        jugadores = list,
                        isLoading = false,
                        mensaje = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            try {
                deleteJugadorUseCase(id)
                onEvent(ListJugadorUiEvent.ShowMessage("Jugador eliminado"))
            } catch (e: Exception) {
                onEvent(ListJugadorUiEvent.ShowMessage("Error al eliminar: ${e.localizedMessage}"))
            }
        }
    }

    fun onNavigationHandled() {
        _state.update {
            it.copy(
                navigateToCreate = false,
                navigateToEditId = null
            )
        }
    }

    fun clearMensaje() {
        _state.update { it.copy(mensaje = null) }
    }
}
