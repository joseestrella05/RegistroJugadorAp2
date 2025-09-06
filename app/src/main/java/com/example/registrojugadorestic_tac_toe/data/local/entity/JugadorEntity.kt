package com.example.registrojugadorestic_tac_toe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class JugadorEntity(
    @PrimaryKey(autoGenerate = true) val jugadorId: Int? = null,
    val nombres: String,
    val partidas: Int,
    val JugadorId: Int?
)
