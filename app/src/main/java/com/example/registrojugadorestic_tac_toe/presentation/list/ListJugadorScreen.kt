package com.example.registrojugadorestic_tac_toe.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registrojugadorestic_tac_toe.domain.model.Jugador

// UI State
data class ListJugadorUiState(
    val jugadores: List<Jugador> = emptyList(),
    val isLoading: Boolean = false
)

// UI Events
sealed interface ListJugadorUiEvent {
    object CreateNew : ListJugadorUiEvent
    data class Edit(val jugadorId: Int) : ListJugadorUiEvent
    data class Delete(val jugadorId: Int) : ListJugadorUiEvent
}

// Screen
@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListJugadorBody(state, viewModel::onEvent)
}

// Body
@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onEvent: (ListJugadorUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ListJugadorUiEvent.CreateNew) },
                modifier = Modifier.testTag("fab_add")
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .testTag("loading")
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .testTag("jugador_list")
            ) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onClick = { onEvent(ListJugadorUiEvent.Edit(jugador.jugadorId)) },
                        onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) }
                    )
                }
            }
        }
    }
}

// Card
@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: (Jugador) -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("jugador_card_${jugador.jugadorId}")
            .clickable { onClick(jugador) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                Text("Partidas: ${jugador.partidas}")
            }
            TextButton(
                onClick = { onDelete(jugador.jugadorId) },
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("Eliminar") }
        }
    }
}

// Preview
@Preview
@Composable
private fun ListJugadorBodyPreview() {
    val jugadores = listOf(
        Jugador(jugadorId = 1, nombres = "Juan", partidas = 5),
        Jugador(jugadorId = 2, nombres = "María", partidas = 10)
    )
    val state = ListJugadorUiState(jugadores = jugadores)
    MaterialTheme {
        ListJugadorBody(state) {}
    }
}
