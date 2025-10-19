package edu.ucne.RegistroJugadorAp2.presentation.partida.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPartidasScreen(
    viewModel: ListPartidasViewModel = hiltViewModel(),
    onNavigateToDetail: (Int) -> Unit
) {
    val partidas by viewModel.partidas.collectAsStateWithLifecycle()
    val jugadores by viewModel.jugadores.collectAsStateWithLifecycle() // 👈 necesitas exponerlos en tu ViewModel

    Scaffold(
        topBar = { TopAppBar(title = { Text("Partidas registradas") }) }
    ) { padding ->
        if (partidas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay partidas registradas", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(partidas) { partida ->
                    PartidasCard(
                        partida = partida,
                        jugadores = jugadores,
                        onClick = { onNavigateToDetail(partida.partidaId) },
                        onDelete = { viewModel.deletePartidas(partida) }
                    )
                }
            }
        }
    }
}

@Composable
fun PartidasCard(
    partida: Partida,
    jugadores: List<Jugador>,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val jugador1 = jugadores.find { it.jugadorId == partida.jugador1Id }?.nombres ?: "Desconocido"
    val jugador2 = jugadores.find { it.jugadorId == partida.jugador2Id }?.nombres ?: "Desconocido"
    val ganador = partida.ganadorId?.let { id ->
        jugadores.find { it.jugadorId == id }?.nombres ?: "Pendiente"
    } ?: "Pendiente"

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("📅 ${partida.fecha}", style = MaterialTheme.typography.bodyMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(6.dp))
                Text("Jugador 1: $jugador1", style = MaterialTheme.typography.bodyLarge)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(6.dp))
                Text("Jugador 2: $jugador2", style = MaterialTheme.typography.bodyLarge)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.width(6.dp))
                Text("Ganador: $ganador", style = MaterialTheme.typography.bodyLarge, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }

            Text("Finalizada: ${if (partida.esFinalizada) "✅ Sí" else "❌ No"}")

            Spacer(Modifier.height(8.dp))

            OutlinedButton(
                onClick = onDelete,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(Modifier.width(4.dp))
                Text("Eliminar")
            }
        }
    }
}
