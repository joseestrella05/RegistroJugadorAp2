package edu.ucne.RegistroJugadorAp2.presentation.list
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

@Composable
fun JugadorCard(
    jugador: Jugador,
    onClick: (Jugador) -> Unit,
    onDelete: (Int) -> Unit
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
                onClick = { onClick(jugador) },
                modifier = Modifier.testTag("edit_button_${jugador.jugadorId}")
            ) { Text("Editar") }

            TextButton(
                onClick = { onDelete(jugador.jugadorId) },
                modifier = Modifier.testTag("delete_button_${jugador.jugadorId}")
            ) { Text("Eliminar") }
        }
    }
}
