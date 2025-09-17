package edu.ucne.RegistroJugadorAp2.presentation.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.ui.theme.RegistroJugadorAp2Theme
import kotlin.text.get


@Composable
fun TicTacToeScreen(
    viewModel: GameViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TicTacToeBody(
        state = state,
        jugadores = state.jugadores,
        onSelectPlayer1 = { viewModel.selectPlayer1(it) },
        onSelectPlayer2 = { viewModel.selectPlayer2(it) },
        startGame = viewModel::startGame,
        onCellClick = viewModel::onCellClick,
        restartGame = viewModel::restartGame
    )
}

@Composable
private fun TicTacToeBody(
    state: GameUiState,
    jugadores: List<Jugador>,
    onSelectPlayer1: (Jugador) -> Unit,
    onSelectPlayer2: (Jugador) -> Unit,
    startGame: () -> Unit,
    onCellClick: (Int) -> Unit,
    restartGame: () -> Unit,
) {
    val jugadoresMap = remember(jugadores) { jugadores.associateBy { it.jugadorId } }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!state.gameStarted) {
                PlayerSelectionScreen(
                    jugadores = jugadores,
                    player1 = state.player1Id?.let { jugadoresMap[it] },
                    player2 = state.player2Id?.let { jugadoresMap[it] },
                    onPlayer1Selected = onSelectPlayer1,
                    onPlayer2Selected = onSelectPlayer2,
                    onStartGame = startGame
                )
            } else {
                GameBoard(
                    uiState = state,
                    jugadores = jugadores,
                    onCellClick = onCellClick,
                    onRestartGame = restartGame
                )
            }
        }
    }
}

@Composable
fun PlayerSelectionScreen(
    jugadores: List<Jugador>,
    player1: Jugador?,
    player2: Jugador?,
    onPlayer1Selected: (Jugador) -> Unit,
    onPlayer2Selected: (Jugador) -> Unit,
    onStartGame: () -> Unit
) {
    Text("Selecciona jugadores", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))

    DropdownJugador(
        label = "Jugador 1",
        jugadores = jugadores,
        selected = player1,
        onSelected = onPlayer1Selected
    )

    Spacer(modifier = Modifier.height(12.dp))

    DropdownJugador(
        label = "Jugador 2",
        jugadores = jugadores,
        selected = player2,
        onSelected = onPlayer2Selected
    )

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = onStartGame,
        enabled = player1 != null && player2 != null && player1.jugadorId != player2.jugadorId
    ) {
        Text("Iniciar partida")
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
        Text(label, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedButton(onClick = { expanded = true }) {
            Text(selected?.nombres ?: "Seleccionar jugador")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (jugadores.isEmpty()) {
                DropdownMenuItem(text = { Text("No hay jugadores") }, onClick = { expanded = false })
            } else {
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
}


@Composable
fun GameBoard(
    uiState: GameUiState,
    jugadores: List<Jugador>,
    onCellClick: (Int) -> Unit,
    onRestartGame: () -> Unit
) {
    val jugadoresMap = remember(jugadores) { jugadores.associateBy { it.jugadorId } }
    val currentPlayerName = uiState.currentPlayerId?.let { jugadoresMap[it]?.nombres } ?: "—"
    val winnerName = uiState.winnerId?.let { jugadoresMap[it]?.nombres }

    val gameStatus = when {
        winnerName != null -> "🏆 ¡Ganador: $winnerName!"
        uiState.isDraw -> "🤝 ¡Es un empate!"
        else -> "Turno de: $currentPlayerName"
    }

    Text(text = gameStatus, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(20.dp))
    GameBoardBody(
        board = uiState.board,
        jugadoresMap = jugadoresMap,
        player1Id = uiState.player1Id,
        player2Id = uiState.player2Id,
        onCellClick = onCellClick
    )
    Spacer(modifier = Modifier.height(20.dp))
    Button(onClick = onRestartGame) {
        Text("Reiniciar Juego", fontSize = 18.sp)
    }
}

@Composable
fun GameBoardBody(
    board: List<Int?>,
    jugadoresMap: Map<Int, Jugador?>,
    player1Id: Int?,
    player2Id: Int?,
    onCellClick: (Int) -> Unit
) {
    Column {
        (0..2).forEach { row ->
            Row {
                (0..2).forEach { col ->
                    val index = row * 3 + col
                    val jugador = board[index]?.let { jugadoresMap[it] }
                    val isP1 = board[index] != null && board[index] == player1Id
                    BoardCell(
                        jugador = jugador,
                        isPlayer1 = isP1,
                        onCellClick = { onCellClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    jugador: Jugador?,
    isPlayer1: Boolean,
    onCellClick: () -> Unit
) {
    // Mostrar X o O en vez de las iniciales
    val display = when {
        jugador == null -> ""
        isPlayer1 -> "X"
        else -> "O"
    }

    val textColor = when {
        jugador == null -> Color.Black
        isPlayer1 -> Color(0xFF0D47A1) // azul P1
        else -> Color(0xFFD32F2F) // rojo P2
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp)
            .background(Color.LightGray)
            .clickable { onCellClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = display,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BoardPreview() {
    val demoPlayers = listOf(
        Jugador(jugadorId = 1, nombres = "Ana Pérez", partidas = 0),
        Jugador(jugadorId = 2, nombres = "Luis Gomez", partidas = 0)
    )
    RegistroJugadorAp2Theme {
        TicTacToeBody(
            state = GameUiState(jugadores = demoPlayers),
            jugadores = demoPlayers,
            onSelectPlayer1 = {},
            onSelectPlayer2 = {},
            startGame = {},
            onCellClick = {},
            restartGame = {}
        )
    }
}
