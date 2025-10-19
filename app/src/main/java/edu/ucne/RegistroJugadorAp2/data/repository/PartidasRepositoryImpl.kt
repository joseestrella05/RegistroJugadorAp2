package edu.ucne.RegistroJugadorAp2.data.repository

import edu.ucne.RegistroJugadorAp2.data.local.dao.PartidaDao
import edu.ucne.RegistroJugadorAp2.data.local.mapper.PartidaMapper
import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import edu.ucne.RegistroJugadorAp2.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartidasRepositoryImpl @Inject constructor(
    private val dao: PartidaDao
) : PartidaRepository {

    override fun getPartidas(): Flow<List<Partida>> =
        dao.getAllPartidas().map { list -> list.map { PartidaMapper.toDomain(it) } }

    override suspend fun getPartidaById(id: Int): Partida? =
        dao.getPartidaById(id)?.let { PartidaMapper.toDomain(it) }

    override suspend fun insertPartida(partida: Partida) =
        dao.insertPartida(PartidaMapper.fromDomain(partida))

    override suspend fun deletePartida(partida: Partida) =
        dao.deletePartida(PartidaMapper.fromDomain(partida))
}