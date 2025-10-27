package edu.ucne.RegistroJugadorAp2.data.remote

import edu.ucne.RegistroJugadorAp2.data.remote.dto.MovimientoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovimientosApi {
    @GET("api/Movimientos/{partidaId}")
    suspend fun getMovimientos(@Path("partidaId") partidaId: Int): List<MovimientoDto>
}