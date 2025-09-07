package edu.ucne.RegistroJugadorAp2.domain.usecase

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository

class GetJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? = repository.getJugador(id)
}