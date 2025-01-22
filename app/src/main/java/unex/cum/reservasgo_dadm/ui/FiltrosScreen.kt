package unex.cum.reservasgo_dadm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import unex.cum.reservasgo_dadm.ui.theme.colorApp

@Composable
fun FiltrosScreen(
    onFiltrar: (List<String>, Float, Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    val tipos_cocina =
        listOf("Mediterránea", "Japonesa", "Mexicana", "Italiana", "Francesa", "Café", "Otros")
    val cocinasSeleccionadas = remember {
        mutableStateMapOf<String, Boolean>().apply {
            tipos_cocina.forEach {
                this[it] = false
            }
        }
    }

    var ratioBusqueda by remember { mutableStateOf(10f) }
    var buscarPorUbicacion by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            // Filtros: Tipo de cocina
            Text("Tipo de cocina", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Filtros: Tipo de cocina
        items(tipos_cocina) { cocina ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = cocinasSeleccionadas[cocina] == true,
                    onCheckedChange = { isChecked ->
                        cocinasSeleccionadas[cocina] = isChecked
                    }
                )
                Text(text = cocina, style = MaterialTheme.typography.bodyMedium)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Filtros: Ubicación
            Text("Ubicación", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Opción para activar o desactivar la búsqueda por ubicación
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = buscarPorUbicacion,
                    onCheckedChange = { buscarPorUbicacion = it }
                )
                Text(text = "Buscar por ubicación", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (buscarPorUbicacion) {
            item {
                Text(
                    text = "Radio de búsqueda: ${ratioBusqueda.toInt()} km",
                    style = MaterialTheme.typography.bodyMedium
                )

                // Slider para el radio de búsqueda
                Slider(
                    value = ratioBusqueda,
                    onValueChange = { ratioBusqueda = it },
                    valueRange = 0f..10f,
                    steps = 19,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = colorApp,
                        activeTrackColor = colorApp
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Botones de acción
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = onDismiss) { Text("Cancelar") }
                Button(onClick = {
                    val filtrosSeleccionados = cocinasSeleccionadas.filter { it.value }.keys.toList()
                    onFiltrar(filtrosSeleccionados, ratioBusqueda, buscarPorUbicacion)
                }) { Text("Filtrar") }
            }
        }
    }
}

