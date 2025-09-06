package com.example.registrojugadorestic_tac_toe.data.local.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrojugadorestic_tac_toe.data.local.dao.JugadorDao
import com.example.registrojugadorestic_tac_toe.data.local.entity.JugadorEntity

@Database(entities = [JugadorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
}
