package unex.cum.reservasgo_dadm.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var pulsado by remember { mutableIntStateOf(0) }

    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                // Imagen de la izquierda (logo de la app)
                IconButton(onClick = { /* Acción al hacer clic en el logo */ }) {}
            },
            title = {
                // Campo de búsqueda centrado
                var query by remember { mutableStateOf("") }

                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Buscar") },
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // Ajustamos el ancho para que no ocupe toda la barra
                        .background(MaterialTheme.colorScheme.surface),
                    singleLine = true
                )
            },
            actions = {
                // Acciones de la derecha (iconos)
                IconButton(onClick = { /* Acción al hacer clic en el icono */ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                }
            }
        )
    }, bottomBar = {
        BottomAppBar() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //TODO añadir iconos
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Home, contentDescription = "Inicio")
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = {}) { }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = {}) {}
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = { pulsado++ }) {
            //TODO cambiar el icono
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(20){index->
                RestaurantItem(index)
            }
        }
    }
}

@Composable
fun RestaurantItem(index: Int) {
    Text(
        modifier = Modifier.padding(16.dp),
        text="Restaurante $index"
    )
}
