package edu.ucne.RegistroJugadorAp2.di

import android.content.Context
import androidx.room.Room
import edu.ucne.RegistroJugadorAp2.data.local.dao.PartidaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroJugadorAp2.data.local.database.AppDatabase
import edu.ucne.RegistroJugadorAp2.data.repository.PartidasRepositoryImpl
import edu.ucne.RegistroJugadorAp2.domain.repository.PartidaRepository
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.DeletePartidaUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.GetAllPartidasUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.GetPartidaUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.InsertPartidaUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.ObservePartidasUseCase
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object AppModulepartidas {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    @Provides
    fun providePartidaDao(db: AppDatabase) = db.partidaDao()

    @Provides
    @Singleton
    fun providePartidaRepository(dao: PartidaDao): PartidaRepository =
        PartidasRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideInsertPartidaUseCase(repo: PartidaRepository) = InsertPartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideDeletePartidaUseCase(repo: PartidaRepository) = DeletePartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideGetAllPartidasUseCase(repo: PartidaRepository) = GetAllPartidasUseCase(repo)

    @Provides
    @Singleton
    fun provideGetPartidaUseCase(repo: PartidaRepository) = GetPartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideObservePartidasUseCase(repo: PartidaRepository) = ObservePartidasUseCase(repo)
}