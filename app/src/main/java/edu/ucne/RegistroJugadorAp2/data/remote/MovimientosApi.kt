package edu.ucne.RegistroJugadorAp2.data.remote

import edu.ucne.RegistroJugadorAp2.domain.model.MovimientoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovimientosApi {
    @GET("api/Movimientos/1")
    suspend fun getMovimientos(): List<MovimientoDto>

    @POST("api/Movimientos/1")
    suspend fun postMovimiento(@Body movimiento: MovimientoDto): MovimientoDto
}