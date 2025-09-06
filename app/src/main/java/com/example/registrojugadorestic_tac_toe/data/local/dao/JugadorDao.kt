package com.example.registrojugadorestic_tac_toe.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Upsert()
    suspend fun save(Jugadores: JugadorEntity)
    @Query("""
        SELECT *
        FROM jugadores
        WHERE jugadorId = :id
        LIMIT 1
    """)
    suspend fun find(id: Int): JugadorEntity?

    @Delete
    suspend fun delete(jugador: JugadorEntity )
    @Query("SELECT * FROM jugadores")
    fun getAll(): Flow<List<JugadorEntity>>


}