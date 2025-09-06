package com.example.registrojugadorestic_tac_toe.data.repository

import com.example.registrojugadorestic_tac_toe.data.local.dao.JugadorDao
import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import  com.example.registrojugadorestic_tac_toe.data.local.mapper.toDomain
import  com.example.registrojugadorestic_tac_toe.data.local.mapper.toEntity
import com.example.registrojugadorestic_tac_toe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JugadorRepositoryImpl(
    private val dao: JugadorDao
) : JugadorRepository {

    override fun observeJugadores(): Flow<List<Jugador>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun getJugador(id: Int): Jugador? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(jugador: Jugador): Int {
        dao.upsert(jugador.toEntity())
        return jugador.jugadorId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}