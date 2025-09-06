package edu.ucne.RegistroJugadorAp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroJugadorAp2.presentation.edit.EditJugadorScreen
import edu.ucne.RegistroJugadorAp2.presentation.list.ListJugadorScreen
import edu.ucne.RegistroJugadorAp2.ui.theme.RegistroJugadorAp2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroJugadorAp2Theme {
                Scaffold (
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Registro de jugadores",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            },
                        )
                    },
                ){
                    Column (
                        modifier = Modifier.fillMaxSize()
                            .padding(it)
                    ) {
                        EditJugadorScreen()
                        ListJugadorScreen()
                    }

                }


            }
        }
    }
}