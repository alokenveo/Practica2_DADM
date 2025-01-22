package unex.cum.reservasgo_dadm.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import unex.cum.reservasgo_dadm.ui.theme.colorApp

@Composable
fun FiltrosScreen(
    onFiltrar: (List<String>, Float) -> Unit,
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

    var ratioBusqueda by remember { mutableStateOf(50f) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Filtros: Tipo de cocina
        Text("Tipo de cocina", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            tipos_cocina.forEach { cocina ->
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
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filtros: Radio de búsqueda
        Text("Ubicación", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Radio de búsqueda: ${ratioBusqueda.toInt()} km",
            style = MaterialTheme.typography.bodyMedium
        )

        // Slider para el radio de búsqueda
        Slider(
            value = ratioBusqueda,
            onValueChange = { ratioBusqueda = it },
            valueRange = 1f..50f, // Ajusta el rango de valores según necesites
            steps = 9, // Pasos entre valores, ajusta según el rango
            modifier = Modifier.padding(horizontal = 8.dp),
            colors = SliderDefaults.colors(
                thumbColor = colorApp,
                activeTrackColor = colorApp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onDismiss) { Text("Cancelar") }
            Button(onClick = {
                val filtrosSeleccionados = cocinasSeleccionadas.filter { it.value }.keys.toList()
                onFiltrar(filtrosSeleccionados, ratioBusqueda)
            }) { Text("Filtrar") }
        }

    }
}
