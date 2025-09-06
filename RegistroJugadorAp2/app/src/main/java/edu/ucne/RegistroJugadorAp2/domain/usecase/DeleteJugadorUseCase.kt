package edu.ucne.RegistroJugadorAp2.domain.usecase

import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository

class DeleteJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
