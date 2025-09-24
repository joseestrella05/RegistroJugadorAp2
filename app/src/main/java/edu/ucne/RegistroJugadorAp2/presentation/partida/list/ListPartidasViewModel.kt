package edu.ucne.RegistroJugadorAp2.presentation.partida.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.RegistroJugadorAp2.domain.model.Partida
import edu.ucne.RegistroJugadorAp2.domain.model.Jugador
import edu.ucne.RegistroJugadorAp2.domain.repository.PartidaRepository
import edu.ucne.RegistroJugadorAp2.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPartidasViewModel @Inject constructor(
    private val partidaRepository: PartidaRepository,
    private val jugadorRepository: JugadorRepository
) : ViewModel() {

    val partidas = partidaRepository.getPartidas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val jugadores = jugadorRepository.observeJugadores()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletePartidas(partida: Partida) {
        viewModelScope.launch {
            partidaRepository.deletePartida(partida)
        }
    }
}

