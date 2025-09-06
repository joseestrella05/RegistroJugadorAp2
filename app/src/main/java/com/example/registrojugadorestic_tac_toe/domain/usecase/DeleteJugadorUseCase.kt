package com.example.registrojugadorestic_tac_toe.domain.usecase

import com.example.registrojugadorestic_tac_toe.domain.repository.JugadorRepository

class DeleteJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}