package edu.ucne.RegistroJugadorAp2.presentation.jugador.list

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val mensaje: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
