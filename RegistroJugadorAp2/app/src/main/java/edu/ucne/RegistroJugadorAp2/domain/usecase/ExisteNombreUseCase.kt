package edu.ucne.RegistroJugadorAp2.domain.usecase

import edu.ucne.RegistroJugadorAp2.data.repository.JugadorRepositoryImpl
import javax.inject.Inject

class ExisteNombreUseCase @Inject constructor(
    private val repository: JugadorRepositoryImpl
) {
    suspend operator fun invoke(nombre: String): Boolean {
        return repository.existeNombre(nombre)
    }
}