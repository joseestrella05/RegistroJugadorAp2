package edu.ucne.RegistroJugadorAp2.domain.usecasepartida

import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import edu.ucne.RegistroJugadorAp2.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPartidasUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    operator fun invoke(): Flow<List<Partida>> {
        return repository.getPartidas()
    }
}