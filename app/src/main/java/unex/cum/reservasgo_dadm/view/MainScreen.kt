package unex.cum.reservasgo_dadm.view


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import unex.cum.reservasgo_dadm.model.Restaurante
import unex.cum.reservasgo_dadm.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val restaurantes by viewModel.restaurantes.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Restaurantes Disponibles", style = MaterialTheme.typography.titleLarge)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(restaurantes.size) { index ->
                RestaurantItem(restaurante = restaurantes[index])
            }
        }
    }
}

@Composable
fun RestaurantItem(restaurante: Restaurante) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = restaurante.nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = "Ubicación: ${restaurante.ubicacion}")
            Text(text = "Tipo: ${restaurante.cocina}")
        }
    }
}

@Preview
@Composable
fun Previo() {
    @Composable
    fun MainScreen(viewModel: MainViewModel = viewModel()) {
        val restaurantes by viewModel.restaurantes.collectAsState()

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Restaurantes Disponibles", style = MaterialTheme.typography.titleLarge)
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(restaurantes.size) { index ->
                    RestaurantItem(restaurante = restaurantes[index])
                }
            }
        }
    }
}
