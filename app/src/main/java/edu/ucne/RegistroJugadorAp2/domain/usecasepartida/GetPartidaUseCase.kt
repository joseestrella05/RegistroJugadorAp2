package edu.ucne.RegistroJugadorAp2.domain.usecasepartida

import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import edu.ucne.RegistroJugadorAp2.domain.repository.PartidaRepository
import javax.inject.Inject

class GetPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(id: Int): Partida? {
        return repository.getPartidaById(id)
    }
}