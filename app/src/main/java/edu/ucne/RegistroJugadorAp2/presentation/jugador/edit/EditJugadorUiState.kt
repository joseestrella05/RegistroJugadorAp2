package edu.ucne.RegistroJugadorAp2.presentation.jugador.edit

data class EditJugadorUiState(
    val jugadorId: Int? = null,
    val nombres: String = "",
    val partidas: String = "",
    val nombresError: String? = null,
    val partidasError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)

