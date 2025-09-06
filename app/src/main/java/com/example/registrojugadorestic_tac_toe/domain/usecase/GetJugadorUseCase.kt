package com.example.registrojugadorestic_tac_toe.domain.usecase

import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import com.example.registrojugadorestic_tac_toe.domain.repository.JugadorRepository

class GetJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? = repository.getJugador(id)
}