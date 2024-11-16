package unex.cum.reservasgo_dadm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import unex.cum.reservasgo_dadm.ui.theme.ReservasGO_DADMTheme
import unex.cum.reservasgo_dadm.view.MainScreen
import unex.cum.reservasgo_dadm.view.ReservasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReservasGO_DADMTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController= rememberNavController()
                    NavHost(navController = navController, startDestination = "mainScreen") {
                        composable("mainScreen") {
                            MainScreen(navController)
                        }
                        composable("reservasScreen") {
                            ReservasScreen(navController)
                        }
                }
            }
        }
    }
}}



