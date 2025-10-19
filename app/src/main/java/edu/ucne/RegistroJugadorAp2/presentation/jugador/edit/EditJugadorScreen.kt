package edu.ucne.RegistroJugadorAp2.presentation.jugador.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditJugadorScreen(
    viewModel: EditJugadorViewModel = hiltViewModel(),
    jugadorId: Int? = null,
    onSaveSuccess: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(jugadorId) {
        viewModel.onEvent(EditJugadorUiEvent.Load(jugadorId))
    }

    if (state.saved) {
        LaunchedEffect(Unit) { onSaveSuccess() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.isNew) "Nuevo Jugador" else "Editar Jugador",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.onEvent(EditJugadorUiEvent.Save) }, // ✅ corregido
                    enabled = !state.isSaving,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_guardar")
                ) {
                    Text("Guardar")
                }

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { viewModel.onEvent(EditJugadorUiEvent.Delete) }, // ✅ corregido
                        enabled = !state.isDeleting,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("btn_eliminar")
                    ) {
                        Text("Eliminar")
                    }
                }
            }
        }
    ) { padding ->
        EditJugadorBody(
            state = state,
            onEvent = viewModel::onEvent, // ✅ pasa bien el onEvent
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun EditJugadorBody(
    state: EditJugadorUiState,
    onEvent: (EditJugadorUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = state.nombres,
                    onValueChange = { onEvent(EditJugadorUiEvent.NombresChanged(it)) },
                    label = { Text("Nombre del Jugador") },
                    isError = state.nombresError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_nombres")
                )
                if (state.nombresError != null) {
                    Text(
                        text = state.nombresError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.partidas,
                    onValueChange = { onEvent(EditJugadorUiEvent.PartidasChanged(it)) },
                    label = { Text("Partidas Jugadas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = state.partidasError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_partidas")
                )
                if (state.partidasError != null) {
                    Text(
                        text = state.partidasError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditJugadorBodyPreview() {
    val state = EditJugadorUiState()
    MaterialTheme {
        EditJugadorBody(state = state, onEvent = {})
    }
}
