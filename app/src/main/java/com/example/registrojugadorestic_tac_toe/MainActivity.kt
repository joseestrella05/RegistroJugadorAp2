package com.example.registrojugadorestic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create  // Para reemplazar Edit
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import com.example.registrojugadorestic_tac_toe.data.AppDatabase
import com.example.registrojugadorestic_tac_toe.data.JugadorEntity
import com.example.registrojugadorestic_tac_toe.ui.theme.RegistroJugadoresTicTactoeTheme
import com.example.registrojugadorestic_tac_toe.data.JugadorDao


class MainActivity : ComponentActivity() {
    private lateinit var appdatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appdatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "Jugador.db"
        ).fallbackToDestructiveMigration().build()

        setContent {
            RegistroJugadoresTicTactoeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        JugadorScreen(
                            appdatabase = appdatabase,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JugadorScreen(
    appdatabase: AppDatabase,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf("") }
    var partidas by remember { mutableStateOf(0) }
    var editingJugador by remember { mutableStateOf<JugadorEntity?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val jugadorList by appdatabase.jugadorDao().getAll()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    Column(modifier = modifier.fillMaxSize()) {

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = editingJugador?.jugadorId?.toString() ?: "0",
                    onValueChange = {},
                    label = { Text("ID Jugador") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    enabled = false
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del jugador") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = partidas.toString(),
                    onValueChange = {
                        partidas = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Partido") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.Black,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            nombre = ""
                            partidas = 0
                            errorMessage = null
                            editingJugador = null
                        },
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.Blue),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Blue
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Nuevo", tint = Color.Blue)
                        Text(" Limpiar")
                    }

                    OutlinedButton(
                        onClick = {
                            if (nombre.isBlank()) {
                                errorMessage = "El nombre no puede estar vacío"
                                return@OutlinedButton
                            }
                            if (partidas <= 0) {
                                errorMessage = "Las partidas deben ser mayor a 0"
                                return@OutlinedButton
                            }

                            scope.launch {
                                try {
                                    saveJugador(
                                        appdatabase,
                                        JugadorEntity(
                                            jugadorId = editingJugador?.jugadorId,
                                            nombres = nombre,
                                            partidas = partidas
                                        )
                                    )
                                    nombre = ""
                                    partidas = 0
                                    editingJugador = null
                                    errorMessage = null
                                } catch (e: Exception) {
                                    errorMessage = "Error al guardar: ${e.localizedMessage}"
                                }
                            }
                        },
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.Blue)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Guardar", tint = Color.Blue)
                        Text(" Guardar")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        jugadorList(
            jugadorList = jugadorList,
            onEditClick = { jugador ->
                nombre = jugador.nombres
                partidas = jugador.partidas
                editingJugador = jugador
            },
            onDeleteClick = { jugador ->
                scope.launch {
                    appdatabase.jugadorDao().delete(jugador)
                }
            }
        )
    }
}

@Composable
fun jugadorList(
    jugadorList: List<JugadorEntity>,
    onEditClick: (JugadorEntity) -> Unit,
    onDeleteClick: (JugadorEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Jugador Registrados",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            items(jugadorList) { jugador ->
                JugadorCard(
                    jugador = jugador,
                    onEditClick = { onEditClick(jugador) },
                    onDeleteClick = { onDeleteClick(jugador) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun JugadorCard(
    jugador: JugadorEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ID: ${jugador.jugadorId}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = jugador.nombres,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Text(
                text = "Partidas: ${jugador.partidas}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            IconButton(onClick = onEditClick) {
                Icon(Icons.Outlined.Edit, contentDescription = "Editar", tint = Color.Green)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Outlined.Delete, contentDescription = "Eliminar", tint = Color.Green)
            }
        }
    }
}

suspend fun saveJugador(appdatabase: AppDatabase, jugador: JugadorEntity) {
    appdatabase.jugadorDao().save(jugador)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistroJugadoresTicTactoeTheme {
        Greeting("Android")
    }
}