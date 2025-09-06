package edu.ucne.RegistroJugadorAp2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.RegistroJugadorAp2.data.local.entities.JugadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao {
    @Query("SELECT * FROM Jugadores ORDER BY JugadorId DESC")
    fun observeAll(): Flow<List<JugadorEntity>>

    @Query("SELECT * FROM Jugadores WHERE JugadorId = :id")
    suspend fun getById(id: Int): JugadorEntity?

    @Upsert
    suspend fun upsert(entity: JugadorEntity)

    @Delete
    suspend fun delete(entity: JugadorEntity)

    @Query("DELETE FROM Jugadores WHERE JugadorId = :id")
    suspend fun deleteById(id: Int)
}