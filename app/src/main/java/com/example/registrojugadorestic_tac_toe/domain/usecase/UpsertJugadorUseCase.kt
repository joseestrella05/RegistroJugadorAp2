package com.example.registrojugadorestic_tac_toe.domain.usecase

import kotlinx.coroutines.flow.first
import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import com.example.registrojugadorestic_tac_toe.domain.repository.JugadorRepository

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        // Obtener todos los jugadores existentes para validar duplicados
        val existingJugadores = repository.observeJugadores()
            .first() // Obtener el valor actual de la lista de jugadores

        // Validar jugador
        val validationResult = validateJugador(jugador, existingJugadores)
        if (!validationResult.isValid) {
            return Result.failure(IllegalArgumentException(validationResult.error))
        }

        // Guardar jugador usando el repositorio
        return runCatching { repository.upsert(jugador) }
    }
}