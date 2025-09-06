package com.example.registrojugadorestic_tac_toe.domain.usecase

import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import com.example.registrojugadorestic_tac_toe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObserveJugadorUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> = repository.observeJugadores()
}