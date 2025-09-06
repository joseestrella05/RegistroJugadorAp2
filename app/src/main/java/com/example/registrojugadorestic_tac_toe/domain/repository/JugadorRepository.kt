package com.example.registrojugadorestic_tac_toe.domain.repository

import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    fun observeJugadores(): Flow<List<Jugador>>
    suspend fun getJugador(id: Int): Jugador?
    suspend fun upsert(jugador: Jugador): Int
    suspend fun delete(id: Int)
}