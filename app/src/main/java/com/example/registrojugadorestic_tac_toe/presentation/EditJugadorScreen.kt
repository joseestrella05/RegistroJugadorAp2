package com.example.registrojugadorestic_tac_toe.presentation


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditJugadorScreen(
    viewModel: EditJugadorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditJugadorBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun EditJugadorBody(
    state: EditJugadorUiState,
    onEvent: (EditJugadorUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
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
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

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
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(16.dp))

            Row {
                Button(
                    onClick = { onEvent(EditJugadorUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_guardar")
                ) { Text("Guardar") }

                Spacer(Modifier.width(8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditJugadorUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.testTag("btn_eliminar")
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditJugadorBodyPreview() {
    val state = EditJugadorUiState()
    MaterialTheme {
        EditJugadorBody(state = state) {}
    }
}