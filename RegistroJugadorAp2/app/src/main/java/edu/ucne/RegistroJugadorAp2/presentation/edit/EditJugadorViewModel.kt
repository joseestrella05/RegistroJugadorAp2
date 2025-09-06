package edu.ucne.RegistroJugadorAp2.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadorAp2.domain.usecase.DeleteJugadorUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecase.GetJugadorUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecase.UpsertJugadorUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecase.ExisteNombreUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecase.validateJugadorUi
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditJugadorViewModel @Inject constructor(
    private val getJugadorUseCase: GetJugadorUseCase,
    private val upsertJugadorUseCase: UpsertJugadorUseCase,
    private val deleteJugadorUseCase: DeleteJugadorUseCase,
    private val existeNombreUseCase: ExisteNombreUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditJugadorUiState())
    val state: StateFlow<EditJugadorUiState> = _state.asStateFlow()

    fun onEvent(event: EditJugadorUiEvent) {
        when (event) {
            is EditJugadorUiEvent.Load -> onLoad(event.id)
            is EditJugadorUiEvent.NombresChanged -> _state.update {
                it.copy(nombres = event.value, nombresError = null)
            }
            is EditJugadorUiEvent.PartidasChanged -> _state.update {
                it.copy(partidas = event.value, partidasError = null)
            }
            EditJugadorUiEvent.Save -> onSave()
            EditJugadorUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, jugadorId = null) }
            return
        }
        viewModelScope.launch {
            val jugador = getJugadorUseCase(id)
            jugador?.let {
                _state.update {
                    it.copy(
                        isNew = false,
                        jugadorId = jugador.jugadorId,
                        nombres = jugador.nombres,
                        partidas = jugador.partidas.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val nombres = state.value.nombres
        val partidas = state.value.partidas
        val validation = validateJugadorUi(nombres, partidas)

        if (!validation.isValid) {
            _state.update {
                it.copy(
                    nombresError = validation.nombresError,
                    partidasError = validation.partidasError
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            //  Validación de nombre duplicado
            val duplicado = existeNombreUseCase(nombres)

            if (duplicado) {
                if (state.value.isNew) {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            nombresError = "Ya existe un jugador con ese nombre"
                        )
                    }
                    return@launch
                }

                val jugadorExistente = getJugadorUseCase(state.value.jugadorId ?: 0)
                if (jugadorExistente != null && jugadorExistente.nombres != nombres) {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            nombresError = "Ya existe un jugador con ese nombre"
                        )
                    }
                    return@launch
                }
            }

            val jugador = Jugador(
                jugadorId = state.value.jugadorId ?: 0,
                nombres = nombres,
                partidas = partidas.toInt()
            )

            val result = upsertJugadorUseCase(jugador)
            result.onSuccess { newId ->
                _state.update { it.copy(isSaving = false, saved = true, jugadorId = newId) }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.jugadorId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteJugadorUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}