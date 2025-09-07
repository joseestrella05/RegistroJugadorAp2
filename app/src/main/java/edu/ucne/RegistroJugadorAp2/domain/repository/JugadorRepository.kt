package edu.ucne.RegistroJugadorAp2.domain.repository

import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJugadores(): Flow<List<Jugador>>
    suspend fun getJugador(id: Int): Jugador?
    suspend fun upsert(jugador: Jugador): Int
    suspend fun delete(id: Int)
    suspend fun existeNombre(nombre: String): Boolean

}
