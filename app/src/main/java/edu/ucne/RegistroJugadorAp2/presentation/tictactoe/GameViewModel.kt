package edu.ucne.RegistroJugadorAp2.presentation.tictactoe


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.usecase.ObserveJugadorUseCase
import edu.ucne.RegistroJugadorAp2.domain.usecasepartida.InsertPartidaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameUiState(
    val jugadores: List<Jugador> = emptyList(),
    val player1Id: Int? = null,
    val player2Id: Int? = null,
    val currentPlayerId: Int? = null,
    val board: List<Int?> = List(9) { null }, // cada celda guarda jugadorId o null
    val winnerId: Int? = null,
    val isDraw: Boolean = false,
    val gameStarted: Boolean = false
)
@HiltViewModel
class GameViewModel @Inject constructor(
    private val observeJugadoresUseCase: ObserveJugadorUseCase,
    private val insertPartidaUseCase: InsertPartidaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameUiState())
    val state: StateFlow<GameUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeJugadoresUseCase().collectLatest { list ->
                _state.update { it.copy(jugadores = list) }
            }
        }
    }

    fun selectPlayer1(jugador: Jugador) {
        _state.update { it.copy(player1Id = jugador.jugadorId) }
    }

    fun selectPlayer2(jugador: Jugador) {
        _state.update { it.copy(player2Id = jugador.jugadorId) }
    }

    fun startGame() {
        val s = state.value
        val p1 = s.player1Id
        val p2 = s.player2Id
        if (p1 == null || p2 == null || p1 == p2) return // no iniciar
        _state.update {
            it.copy(
                board = List(9) { null },
                winnerId = null,
                isDraw = false,
                currentPlayerId = p1,
                gameStarted = true
            )
        }
    }
    private fun savePartida(winnerId: Int?) {
        val s = state.value
        val fecha = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())

        val partida = edu.ucne.RegistroJugadorAp2.domain.model.Partida(
            partidaId = 0,
            fecha = fecha,
            jugador1Id = s.player1Id ?: return,
            jugador2Id = s.player2Id ?: return,
            ganadorId = winnerId,
            esFinalizada = true
        )

        viewModelScope.launch {
            insertPartidaUseCase(partida)
        }
    }

    fun onCellClick(index: Int) {
        val s = state.value
        if (!s.gameStarted) return
        if (s.board[index] != null) return
        if (s.winnerId != null) return

        val current = s.currentPlayerId ?: return
        val newBoard = s.board.toMutableList()
        newBoard[index] = current

        val winner = checkWinner(newBoard)
        if (winner != null) {
            _state.update { it.copy(board = newBoard, winnerId = winner) }
            savePartida(winner)
            return
        }

        if (newBoard.all { it != null }) {
            _state.update { it.copy(board = newBoard, isDraw = true) }
            savePartida(null)
            return
        }

        val next = if (current == s.player1Id) s.player2Id else s.player1Id
        _state.update { it.copy(board = newBoard, currentPlayerId = next) }
    }


    private fun checkWinner(board: List<Int?>): Int? {
        val lines = arrayOf(
            intArrayOf(0,1,2), intArrayOf(3,4,5), intArrayOf(6,7,8),
            intArrayOf(0,3,6), intArrayOf(1,4,7), intArrayOf(2,5,8),
            intArrayOf(0,4,8), intArrayOf(2,4,6)
        )
        for (line in lines) {
            val a = board[line[0]] ?: continue
            val b = board[line[1]] ?: continue
            val c = board[line[2]] ?: continue
            if (a == b && b == c) return a
        }
        return null
    }

    fun restartGame() {
        val p1 = state.value.player1Id
        if (p1 == null) {
            _state.update {
                it.copy(
                    board = List(9) { null },
                    winnerId = null,
                    isDraw = false,
                    gameStarted = false,
                    currentPlayerId = null
                )
            }
        } else {
            _state.update {
                it.copy(
                    board = List(9) { null },
                    winnerId = null,
                    isDraw = false,
                    gameStarted = true,
                    currentPlayerId = p1
                )
            }
        }
    }
}