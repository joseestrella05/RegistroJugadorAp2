package edu.ucne.RegistroJugadorAp2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroJugadorAp2.data.local.dao.JugadorDao
import edu.ucne.RegistroJugadorAp2.data.local.dao.PartidaDao
import edu.ucne.RegistroJugadorAp2.data.local.entities.JugadorEntity
import edu.ucne.RegistroJugadorAp2.data.local.entities.PartidaEntity

@Database(entities = [JugadorEntity::class, PartidaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
}