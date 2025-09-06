package com.example.registrojugadorestic_tac_toe.presentation.list

import com.example.registrojugadorestic_tac_toe.domain.model.Jugador

data class ListJugadorUiState(
    val isLoading: Boolean = false,
    val jugadores: List<Jugador> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)