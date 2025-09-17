package edu.ucne.RegistroJugadorAp2.presentation.jugador.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToTicTacToe: () -> Unit,
    onNavigateToPartidas: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.navigateToCreate) {
        onNavigateToCreate()
        viewModel.onNavigationHandled()
    }

    state.navigateToEditId?.let { id ->
        onNavigateToEdit(id)
        viewModel.onNavigationHandled()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de jugadores") })
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp), // espacio entre botones
                horizontalAlignment = Alignment.End
            ) {
                // FAB para agregar jugador
                FloatingActionButton(
                    onClick = { viewModel.onEvent(ListJugadorUiEvent.CreateNew) },
                    modifier = Modifier.testTag("fab_create_jugador")
                ) {
                    Text("+")
                }

                // FAB para jugar TicTacToe
                FloatingActionButton(
                    onClick = { onNavigateToTicTacToe() },
                    modifier = Modifier.testTag("fab_play_tictactoe")
                ) {
                    Text("🎮")
                }
                FloatingActionButton(
                    onClick = { onNavigateToPartidas() },
                    modifier = Modifier.testTag("fab_view_partidas")
                ) {
                    Text("📜")
                }
            }
        }

    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.testTag("loading"))
            }
        } else if (state.jugadores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay jugadores registrados")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .testTag("jugador_list")
            ) {
                items(state.jugadores) { jugador ->
                    JugadorCard(
                        jugador = jugador,
                        onClick = {
                            viewModel.onEvent(ListJugadorUiEvent.Edit(jugador.jugadorId))
                        },
                        onDelete = {
                            viewModel.onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId))
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
            .testTag("jugador_card_${jugador.jugadorId}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(jugador.nombres, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Partidas: ${jugador.partidas}")
            }

            TextButton(
                onClick = onClick,
                modifier = Modifier.testTag("edit_button_${jugador.jugadorId}")
            ) { Text("Editar") }

            Spacer(modifier = Modifier.width(8.dp))

            TextButton(
                onClick = onDelete,
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("Eliminar") }
        }
    }
}
