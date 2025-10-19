package edu.ucne.RegistroJugadorAp2.presentation.jugador.list

sealed interface ListJugadorUiEvent {
    data object Load : ListJugadorUiEvent
    data class Delete(val jugadorId: Int) : ListJugadorUiEvent
    data object CreateNew : ListJugadorUiEvent
    data class Edit(val jugadorId: Int) : ListJugadorUiEvent
    data class ShowMessage(val message: String) : ListJugadorUiEvent
}
