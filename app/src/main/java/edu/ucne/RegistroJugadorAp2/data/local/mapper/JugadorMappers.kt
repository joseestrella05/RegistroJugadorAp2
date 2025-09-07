package edu.ucne.RegistroJugadorAp2.data.local.mapper

import edu.ucne.RegistroJugadorAp2.data.local.entities.JugadorEntity
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = JugadorId,
    nombres = nombres,
    partidas = partidas
)

fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    JugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)
