package edu.ucne.RegistroJugadorAp2.data.remote

import edu.ucne.RegistroJugadorAp2.data.remote.dto.PartidaDto
import retrofit2.http.GET


interface PartidasApi {
    @GET("api/Partidas")
    suspend fun getPartidas(): List<PartidaDto>
}