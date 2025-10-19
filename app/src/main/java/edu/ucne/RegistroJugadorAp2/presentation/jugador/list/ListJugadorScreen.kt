package edu.ucne.RegistroJugadorAp2.presentation.jugador.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var selectedItem by remember { mutableStateOf(0) } // para controlar selección de la barra

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
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        viewModel.onEvent(ListJugadorUiEvent.CreateNew)
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
                    label = { Text("Nuevo") }
                )
                NavigationBarItem(
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        onNavigateToTicTacToe()
                    },
                    icon = { Icon(Icons.Default.Games, contentDescription = "Jugar") },
                    label = { Text("Juego") }
                )
                NavigationBarItem(
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        onNavigateToPartidas()
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = "Partidas") },
                    label = { Text("Partidas") }
                )
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
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 📌 Info del jugador
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = jugador.nombres,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Partidas: ${jugador.partidas}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.testTag("edit_button_${jugador.jugadorId}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}