package edu.ucne.RegistroJugadorAp2.presentation.edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditJugadorScreen(
    jugadorId: Int,
    onBackWithMessage: (String) -> Unit,   // 👈 antes era onBack()
    viewModel: EditJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(jugadorId) {
        viewModel.onEvent(EditJugadorUiEvent.Load(jugadorId))
    }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            viewModel.onSaveHandled()
            onBackWithMessage("Jugador guardado correctamente") // 👈 envía mensaje y vuelve
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            viewModel.onDeleteHandled()
            onBackWithMessage("Jugador eliminado")
        }
    }

    // Si quieres mantener snackbars locales aquí, puedes, pero ya no son necesarios
    EditJugadorBody(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarHostState = remember { SnackbarHostState() } // opcional
    )
}
