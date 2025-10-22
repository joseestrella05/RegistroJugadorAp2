package edu.ucne.RegistroJugadorAp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import edu.ucne.RegistroJugadorAp2.presentation.navigation.RegistroJugadoresNavHost
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroJugadorAp2.presentation.jugador.edit.EditJugadorScreen
import edu.ucne.RegistroJugadorAp2.presentation.jugador.list.ListJugadorScreen
import edu.ucne.RegistroJugadorAp2.presentation.tictactoe.TicTacToeScreen
import edu.ucne.RegistroJugadorAp2.ui.theme.RegistroJugadorAp2Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RegistroJugadorAp2Theme {
                val navController = rememberNavController()

                RegistroJugadoresNavHost(navController)
            }
        }
    }
}