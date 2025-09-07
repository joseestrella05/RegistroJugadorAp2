package edu.ucne.RegistroJugadorAp2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jugadores")
data class JugadorEntity(
    @PrimaryKey(autoGenerate = true)
    val JugadorId: Int = 0,
    val nombres: String,
    val partidas: Int
)