package edu.ucne.RegistroJugadorAp2.presentation.edit

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditJugadorScreen(
    jugadorId: Int,
    onBackWithMessage: (String) -> Unit,
    viewModel: EditJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(jugadorId) {
        viewModel.onEvent(EditJugadorUiEvent.Load(jugadorId))
    }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            viewModel.onSaveHandled()
            onBackWithMessage("Jugador guardado correctamente")
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            viewModel.onDeleteHandled()
            onBackWithMessage("Jugador eliminado")
        }
    }

    EditJugadorBody(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarHostState = remember { SnackbarHostState() }
    )
}
