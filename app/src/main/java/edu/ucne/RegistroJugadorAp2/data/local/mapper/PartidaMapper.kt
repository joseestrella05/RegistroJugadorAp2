package edu.ucne.RegistroJugadorAp2.data.local.mapper

import edu.ucne.RegistroJugadorAp2.data.local.entities.PartidaEntity
import edu.ucne.RegistroJugadorAp2.domain.model.Partida


object PartidaMapper {
    fun toDomain(entity: PartidaEntity): Partida = Partida(
        partidaId = entity.partidaId,
        fecha = entity.fecha,
        jugador1Id = entity.jugador1Id,
        jugador2Id = entity.jugador2Id,
        ganadorId = entity.ganadorId,
        esFinalizada = entity.esFinalizada
    )

    fun fromDomain(domain: Partida): PartidaEntity = PartidaEntity(
        partidaId = domain.partidaId,
        fecha = domain.fecha,
        jugador1Id = domain.jugador1Id,
        jugador2Id = domain.jugador2Id,
        ganadorId = domain.ganadorId,
        esFinalizada = domain.esFinalizada
    )
}