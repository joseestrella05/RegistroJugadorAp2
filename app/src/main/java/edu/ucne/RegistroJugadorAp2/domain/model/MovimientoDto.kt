package edu.ucne.RegistroJugadorAp2.domain.model

data class MovimientoDto(
    val movimientoId: Int,
    val jugador: String,
    val posicionFila: Int,
    val posicionColumna: Int
)