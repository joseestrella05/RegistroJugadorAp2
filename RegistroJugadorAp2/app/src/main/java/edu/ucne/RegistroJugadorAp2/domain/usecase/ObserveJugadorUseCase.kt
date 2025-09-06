package edu.ucne.RegistroJugadorAp2.domain.usecase

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObserveJugadorUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> = repository.observeJugadores()
}