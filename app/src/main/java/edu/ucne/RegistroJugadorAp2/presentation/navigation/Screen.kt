package edu.ucne.RegistroJugadorAp2.presentation.navigation

sealed class Screen(val route: String) {

    // Lista de jugadores
    object JugadorList : Screen("jugador_list")

    // Tic Tac Toe
    object TicTacToe : Screen("tic_tac_toe")

    // Crear/Editar jugador
    data class EditJugador(val jugadorId: Int?) : Screen(
        jugadorId?.let { "edit_jugador/$it" } ?: "edit_jugador/null"
    )

    // Lista de partidas (opcional si más adelante agregas partidas)
    object PartidaList : Screen("partida_list")

    // Crear/Editar partida (opcional)
    data class EditPartida(val partidaId: Int?) : Screen(
        partidaId?.let { "edit_partida/$it" } ?: "edit_partida/null"
    )
}


