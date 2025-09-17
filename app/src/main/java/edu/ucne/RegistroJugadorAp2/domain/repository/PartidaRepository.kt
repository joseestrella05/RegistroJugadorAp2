package edu.ucne.RegistroJugadorAp2.domain.repository

import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    fun getPartidas(): Flow<List<Partida>>
    suspend fun getPartidaById(id: Int): Partida?
    suspend fun insertPartida(partida: Partida)
    suspend fun deletePartida(partida: Partida)
}