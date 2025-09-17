package edu.ucne.RegistroJugadorAp2.data.local.dao

import androidx.room.*
import edu.ucne.RegistroJugadorAp2.data.local.entities.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {
    @Query("SELECT * FROM partidas ORDER BY partidaId DESC")
    fun getAllPartidas(): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas WHERE partidaId = :id")
    suspend fun getPartidaById(id: Int): PartidaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartida(partida: PartidaEntity)

    @Delete
    suspend fun deletePartida(partida: PartidaEntity)
}