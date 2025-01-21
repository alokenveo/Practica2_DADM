package unex.cum.reservasgo_dadm.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.network.RetrofitClient
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import unex.cum.reservasgo_dadm.viewmodel.FavoritosVM
import unex.cum.reservasgo_dadm.viewmodel.FavoritosVMFactory
import unex.cum.reservasgo_dadm.viewmodel.ReservaVM
import unex.cum.reservasgo_dadm.viewmodel.ReservaVMFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestauranteScreen(
    navController: NavHostController,
    usuarioId: Int,
    restauranteId: Int,
    favoritosVM: FavoritosVM = viewModel(factory = FavoritosVMFactory())
) {

    var restaurante by remember { mutableStateOf<Restaurante?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var hacerReserva by remember { mutableStateOf(false) }
    val esFavorito = remember { mutableStateOf(false) }
    val reservasVM: ReservaVM = viewModel(factory = ReservaVMFactory())

    LaunchedEffect(restauranteId) {
        val repo = ReservasGoRepository(RetrofitClient.api)
        restaurante = repo.obtenerRestaurantePorId(restauranteId)
        favoritosVM.esFavorito(usuarioId, restaurante!!.id_restaurante)
        esFavorito.value = favoritosVM.esFavoritoResult.value
        isLoading = false
    }

    Scaffold(
        topBar = {
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
        },
        bottomBar = {
            BottomAppBar(
                containerColor = colorApp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.navigate("mainScreen")
                    }) {
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            } else {
                restaurante?.let { res ->
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                            Log.d("URL_FOTO", "url: ${res.foto}")
                            Image(
                                painter = rememberAsyncImagePainter(model = res.foto),
                                contentDescription = "Foto del restaurante",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp)
                            )
                            Text(
                                res.nombre,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row() {
                                    Icon(Icons.Default.Star, contentDescription = "Rating")
                                    Text(
                                        fontSize = 12.sp,
                                        text = "${res.rating_promedio}",
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                                Row() {
                                    Icon(Icons.Default.LocationOn, contentDescription = "Dirección")
                                    Text(
                                        fontSize = 12.sp,
                                        text = res.direccion,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Icon(
                                        Icons.Default.Restaurant,
                                        contentDescription = "Tipo de cocina"
                                    )
                                    Text(
                                        fontSize = 12.sp,
                                        text = res.tipo_cocina,
                                        modifier = Modifier.align(Alignment.CenterVertically)
                                    )
                                }
                                Row {
                                    IconButton(
                                        onClick = {
                                            if (esFavorito.value == false) {
                                                favoritosVM.agregarFavorito(
                                                    usuarioId,
                                                    restauranteId
                                                )
                                            } else {
                                                favoritosVM.eliminarFavorito(
                                                    usuarioId,
                                                    restauranteId
                                                )
                                            }
                                        }
                                    ) {
                                        Icon(
                                            if (esFavorito.value) Icons.Default.Star else Icons.Default.StarOutline,
                                            contentDescription = "Favoritos"
                                        )
                                    }
                                    Text(
                                        fontSize = 12.sp,
                                        text = "Añadir a favoritos",
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        textAlign = TextAlign.Justify
                                    )
                                }
                            }
                            Text(
                                text = res.descripcion,
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 20.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                    Button(
                        onClick = {
                            hacerReserva = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp, vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorApp
                        ),
                    ) {
                        Text(text = "Reservar")
                    }
                }
            }
        }

        if (hacerReserva) {
            AlertDialog(
                onDismissRequest = { hacerReserva = false },
                title = { Text("Reservar restaurante") },
                text = {
                    ReservaScreen(
                        reservasVM,
                        usuarioId,
                        restauranteId,
                        { hacerReserva = false })
                },
                confirmButton = {}
            )
        }
    }
}