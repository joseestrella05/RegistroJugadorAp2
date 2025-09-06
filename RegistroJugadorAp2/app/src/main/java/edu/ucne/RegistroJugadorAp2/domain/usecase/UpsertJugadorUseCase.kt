package edu.ucne.RegistroJugadorAp2.domain.usecase

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.first

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        // Obtener todos los jugadores existentes para validar duplicados
        val existingJugadores = repository.observeJugadores()
            .first() // Obtener el valor actual de la lista de jugadores

        // Validar jugador
        val validationResult = validateJugadorUi(jugador.nombres, jugador.partidas.toString())
        if (!validationResult.isValid) {
            return Result.failure(IllegalArgumentException(validationResult.nombresError))
        }

        // Guardar jugador usando el repositorio
        return runCatching { repository.upsert(jugador) }
    }
}
