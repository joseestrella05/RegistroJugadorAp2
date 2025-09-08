package edu.ucne.RegistroJugadorAp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroJugadorAp2.presentation.Routes
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
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Registro de jugadores",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = Routes.List,
                        modifier = Modifier.padding(paddingValues)
                    ) {

                        composable(Routes.List) {
                            val backEntry by navController.currentBackStackEntryAsState()
                            val savedStateHandle = backEntry?.savedStateHandle

                            val msgFlow = remember(savedStateHandle) {
                                savedStateHandle?.getStateFlow("snackbar_msg", "")
                            }
                            val msgFromEdit by msgFlow?.collectAsState() ?: remember { mutableStateOf("") }

                            LaunchedEffect(msgFromEdit) {
                                if (msgFromEdit.isNotEmpty()) {
                                    snackbarHostState.showSnackbar(msgFromEdit)
                                    savedStateHandle?.set("snackbar_msg", "")
                                }
                            }

                            ListJugadorScreen(
                                onNavigateToEdit = { jugadorId ->
                                    navController.navigate("${Routes.Edit}/$jugadorId")
                                }
                            )
                        }

                        composable(
                            route = "${Routes.Edit}/{jugadorId}",
                            arguments = listOf(
                                navArgument("jugadorId") {
                                    type = NavType.IntType
                                    defaultValue = 0
                                }
                            )
                        ) { backEntry ->
                            val jugadorId = backEntry.arguments?.getInt("jugadorId") ?: 0

                            EditJugadorScreen(
                                jugadorId = jugadorId,
                                onBackWithMessage = { message ->
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set("snackbar_msg", message)
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
