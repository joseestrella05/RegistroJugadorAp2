package edu.ucne.RegistroJugadorAp2.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditJugadorBody(
    state: EditJugadorUiState,
    onEvent: (EditJugadorUiEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
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
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onEvent(EditJugadorUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f).testTag("btn_guardar")
                ) { Text("Guardar") }

                Spacer(Modifier.width(8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditJugadorUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f).testTag("btn_eliminar")
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}
