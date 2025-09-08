package edu.ucne.RegistroJugadorAp2.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorBody
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiEvent
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiState

@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel(),
    onNavigateToEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.mensaje) {
        state.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMensaje()
        }
    }

    ListJugadorBody(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateToEdit = onNavigateToEdit
    )
}

