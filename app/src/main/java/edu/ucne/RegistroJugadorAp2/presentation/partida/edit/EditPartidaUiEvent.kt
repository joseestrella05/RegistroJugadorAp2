package edu.ucne.RegistroJugadorAp2.presentation.partida.edit

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

sealed class EditPartidaUiEvent {
    data class SelectJugador1(val jugador: Jugador) : EditPartidaUiEvent()
    data class SelectJugador2(val jugador: Jugador) : EditPartidaUiEvent()
    data class SelectGanador(val jugador: Jugador) : EditPartidaUiEvent()
    data class ToggleFinalizada(val value: Boolean) : EditPartidaUiEvent()
    object SavePartida : EditPartidaUiEvent()
}