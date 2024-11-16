package unex.cum.reservasgo_dadm.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import unex.cum.reservasgo_dadm.view.cards.RestauranteCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                // Imagen de la izquierda (logo de la app)
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Logo de la app",
                    modifier = Modifier.height((45.dp))
                )
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
                IconButton(onClick = {
                    navController.navigate("notificacionesScreen")
                }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorApp
            )
        )
    }, bottomBar = {
        BottomAppBar(
            containerColor = colorApp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = "Inicio",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
                //TODO añadir iconos
                IconButton(onClick = {
                    navController.navigate("reservasScreen")
                }) {
                    Icon(
                        Icons.AutoMirrored.Filled.Assignment,
                        contentDescription = "Reservas",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(50.dp))
                IconButton(onClick = {
                    navController.navigate("usuarioScreen")
                }) {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Perfil de Usuario",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { },
            containerColor = colorApp
        ) {
            //TODO añadir iconos
            Icon(Icons.Default.Tune, contentDescription = "Filtros")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(20) { index ->
                RestauranteCard(index,navController)
            }
        }
    }
}

