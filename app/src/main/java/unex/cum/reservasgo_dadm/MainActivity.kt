package unex.cum.reservasgo_dadm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import unex.cum.reservasgo_dadm.ui.theme.ReservasGO_DADMTheme
import unex.cum.reservasgo_dadm.ui.FavoritosScreen
import unex.cum.reservasgo_dadm.ui.LoginScreen
import unex.cum.reservasgo_dadm.ui.MainScreen
import unex.cum.reservasgo_dadm.ui.NotificacionesScreen
import unex.cum.reservasgo_dadm.ui.ReservasScreen
import unex.cum.reservasgo_dadm.ui.RestauranteScreen
import unex.cum.reservasgo_dadm.ui.UsuarioScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReservasGO_DADMTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "mainScreen") {
                        composable("mainScreen") {
                            MainScreen(navController)
                        }
                        composable("reservasScreen") {
                            ReservasScreen(navController)
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
                            LoginScreen()
                        }
                    }
                }
            }
        }
    }
}



