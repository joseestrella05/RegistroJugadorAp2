package edu.ucne.RegistroJugadorAp2.presentation.list

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.presentation.edit.EditJugadorUiEvent
import edu.ucne.RegistroJugadorAp2.presentation.edit.EditJugadorViewModel
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiEvent
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorUiState
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorViewModel


@Composable
fun ListJugadorScreen(
    viewModel: ListJugadorViewModel = hiltViewModel(),
    viewModelCrear: EditJugadorViewModel = hiltViewModel()

) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListJugadorBody(state, viewModel::onEvent, viewModelCrear::onEvent)
}

@Composable
fun ListJugadorBody(
    state: ListJugadorUiState,
    onEvent: (ListJugadorUiEvent) -> Unit,
    onEventCrear: (EditJugadorUiEvent) -> Unit
) {

    Box(
        modifier = Modifier
            .padding(8.dp)
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
                    onDelete = { onEvent(ListJugadorUiEvent.Delete(jugador.jugadorId)) },
                    load = {onEventCrear(EditJugadorUiEvent.Load(jugador.jugadorId))}
                )
            }
        }
    }

}

@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: (Jugador) -> Unit,
    onDelete: (Int) -> Unit,
    load: (Int) -> Unit
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
                onClick = { load(jugador.jugadorId) },
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("editar") }

            TextButton(
                onClick = { onDelete(jugador.jugadorId) },
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("Eliminar") }
        }
    }
}
