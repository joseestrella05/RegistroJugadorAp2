package edu.ucne.RegistroJugadorAp2.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiState
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onEvent: (ListJugadorUiEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateToEdit: (Int) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("Jugadores") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(0) } // 0 = crear nuevo jugador
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar jugador"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onClick = { onNavigateToEdit(jugador.jugadorId) },
                        onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) }
                    )
                }
            }
        }
    }
}
