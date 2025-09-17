package edu.ucne.RegistroJugadorAp2.presentation.partida.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

@Composable
fun EditPartidaScreen(
    viewModel: EditPartidaViewModel = hiltViewModel(),
    partidaId: Int? = null,
    onSaveSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(partidaId) {
        viewModel.loadPartida(partidaId)
    }

    if (state.saved) {
        LaunchedEffect(Unit) {
            onSaveSuccess()
        }
    }

    EditPartidaBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun EditPartidaBody(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Editar Partida", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        DropdownJugador(
            label = "Jugador 1",
            jugadores = state.jugadores,
            selected = state.jugador1,
            onSelected = { onEvent(EditPartidaUiEvent.SelectJugador1(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownJugador(
            label = "Jugador 2",
            jugadores = state.jugadores,
            selected = state.jugador2,
            onSelected = { onEvent(EditPartidaUiEvent.SelectJugador2(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownJugador(
            label = "Ganador",
            jugadores = state.jugadores,
            selected = state.ganador,
            onSelected = { onEvent(EditPartidaUiEvent.SelectGanador(it)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = state.esFinalizada,
                onCheckedChange = { onEvent(EditPartidaUiEvent.ToggleFinalizada(it)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Finalizada")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onEvent(EditPartidaUiEvent.SavePartida) },
            enabled = state.jugador1 != null && state.jugador2 != null
        ) {
            Text("Guardar Partida")
        }
    }
}

@Composable
fun DropdownJugador(
    label: String,
    jugadores: List<Jugador>,
    selected: Jugador?,
    onSelected: (Jugador) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected?.nombres ?: "Seleccionar jugador")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            jugadores.forEach { jugador ->
                DropdownMenuItem(
                    text = { Text(jugador.nombres) },
                    onClick = {
                        onSelected(jugador)
                        expanded = false
                    }
                )
            }
        }
    }
}