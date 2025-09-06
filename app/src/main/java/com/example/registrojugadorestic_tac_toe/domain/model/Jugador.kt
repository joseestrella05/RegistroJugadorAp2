package com.example.registrojugadorestic_tac_toe.domain.model


data class Jugador(
    val jugadorId: Int? = 0,
    val nombres: String,
    val partidas: Int
)