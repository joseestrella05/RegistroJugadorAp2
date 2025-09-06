package edu.ucne.RegistroJugadorAp2.data.repository

import edu.ucne.RegistroJugadorAp2.data.local.dao.JugadorDao
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository
import edu.ucne.RegistroJugadorAp2.data.local.mapper.toDomain
import edu.ucne.RegistroJugadorAp2.data.local.mapper.toEntity
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
