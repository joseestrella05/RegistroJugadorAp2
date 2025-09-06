package edu.ucne.RegistroJugadorAp2.presentation.list

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)