package unex.cum.reservasgo_dadm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.network.SessionManager
import unex.cum.reservasgo_dadm.ui.theme.ReservasGO_DADMTheme
import unex.cum.reservasgo_dadm.ui.FavoritosScreen
import unex.cum.reservasgo_dadm.ui.LoginScreen
import unex.cum.reservasgo_dadm.ui.MainScreen
import unex.cum.reservasgo_dadm.ui.NotificacionesScreen
import unex.cum.reservasgo_dadm.ui.RegisterScreen
import unex.cum.reservasgo_dadm.ui.ReservasScreen
import unex.cum.reservasgo_dadm.ui.RestauranteScreen
import unex.cum.reservasgo_dadm.ui.UsuarioScreen
import unex.cum.reservasgo_dadm.viewmodel.ReservasVM
import unex.cum.reservasgo_dadm.viewmodel.ReservasVMFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this) // Inicializar SessionManager
        var userId by mutableStateOf(0) // MutableState para actualizar la UI

        // Recuperar el ID del usuario en una corrutina
        lifecycleScope.launch {
            userId = sessionManager.getUserId()
        }

        enableEdgeToEdge()
        setContent {
            ReservasGO_DADMTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "loginScreen") {
                        composable("mainScreen") {
                            MainScreen(navController)
                        }
                        composable("reservasScreen/{idUsuario}") { backStackEntry ->
                            val reservasVM: ReservasVM = viewModel(factory = ReservasVMFactory())
                            ReservasScreen(navController, reservasVM, userId)
                        }
                        composable("notificacionesScreen") {
                            NotificacionesScreen(navController)
                        }
                        composable("favoritosScreen") {
                            FavoritosScreen(navController)
                        }
                        composable("usuarioScreen") {
                            UsuarioScreen(navController)
                        }
                        composable("restauranteScreen/{restauranteId}",
                            arguments = listOf(navArgument("restauranteId") {
                                type = NavType.IntType
                            })
                        ) {backStackEntry ->
                            val restauranteId=backStackEntry.arguments?.getInt("restauranteId")?:0
                            RestauranteScreen(navController,restauranteId)
                        }
                        composable("loginScreen") {
                            LoginScreen(navController)
                        }
                        composable("registerScreen") {
                            RegisterScreen(navController)
                        }
                    }
                }
            }
        }
    }
}



