package edu.ucne.RegistroJugadorAp2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroJugadorAp2.data.local.dao.JugadorDao
import edu.ucne.RegistroJugadorAp2.data.local.entities.JugadorEntity

@Database(
    entities = [
        JugadorEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract  class JugadorDatabase : RoomDatabase(){
    abstract fun JugadorDao(): JugadorDao
}