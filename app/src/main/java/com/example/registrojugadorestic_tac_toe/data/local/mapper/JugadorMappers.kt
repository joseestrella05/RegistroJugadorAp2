package com.example.registrojugadorestic_tac_toe.data.local.mapper

import com.example.registrojugadorestic_tac_toe.domain.model.Jugador
import com.example.registrojugadorestic_tac_toe.data.local.entity.JugadorEntity

fun JugadorEntity.toDomain(): Jugador = Jugador(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas
)

fun Jugador.toEntity(): JugadorEntity = JugadorEntity(
    nombres = nombres,
    partidas = partidas,
    JugadorId = jugadorId
)