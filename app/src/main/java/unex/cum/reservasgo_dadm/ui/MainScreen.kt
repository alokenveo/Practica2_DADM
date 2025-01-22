package unex.cum.reservasgo_dadm.ui

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import unex.cum.reservasgo_dadm.ui.cards.RestauranteCard
import unex.cum.reservasgo_dadm.viewmodel.MainVM
import unex.cum.reservasgo_dadm.viewmodel.MainVMFactory
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import unex.cum.reservasgo_dadm.data.model.Restaurante
import java.io.IOException
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    usuarioId: Int,
    mainVM: MainVM = viewModel(factory = MainVMFactory())
) {
    val restaurantes by mainVM.restaurantes.collectAsState()
    val context = LocalContext.current
    var mostrarFiltros by remember { mutableStateOf(false) }
    var filtrosSeleccionados by remember { mutableStateOf<List<String>>(emptyList()) }
    val userLocation = rememberUserLocation()
    var ratioBusqueda by remember { mutableStateOf(10f) }
    var buscarPorUbicacion by remember { mutableStateOf(false) }

    val restaurantesFiltrados = remember {
        mutableStateOf<List<Restaurante>>(emptyList())
    }

    LaunchedEffect(filtrosSeleccionados, buscarPorUbicacion, ratioBusqueda, restaurantes) {
        val filteredRestaurants = restaurantes.filter { restaurante ->
            val cumpleFiltroCocina =
                filtrosSeleccionados.isEmpty() || filtrosSeleccionados.contains(restaurante.tipo_cocina)
            val cumpleFiltroDistancia = if (buscarPorUbicacion) {
                val restauranteLatLng = getLatLngFromAddress(context, restaurante.direccion)
                if (restauranteLatLng != null && userLocation != null) {
                    val distancia = calcularDistancia(userLocation, restauranteLatLng)
                    Log.d(
                        "FILTRADO RESTAURANTES",
                        "Distancia para ${restaurante.nombre}: $distancia km"
                    )
                    distancia <= ratioBusqueda
                } else {
                    Log.e(
                        "FILTRADO RESTAURANTES",
                        "No se pudo obtener latitud/longitud de la dirección: ${restaurante.direccion}"
                    )
                    false
                }
            } else {
                true
            }

            cumpleFiltroCocina && cumpleFiltroDistancia
        }

        restaurantesFiltrados.value = filteredRestaurants
        Log.d("MainScreen", "Restaurantes filtrados: ${filteredRestaurants.size}")
    }

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
            onClick = {
                mostrarFiltros = true
            },
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
            if (restaurantes.isEmpty()) {
                item {
                    Text(
                        text = "No hay restaurantes disponibles",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(restaurantesFiltrados.value) { restaurante ->
                    RestauranteCard(restaurante, navController, usuarioId)
                }
            }
        }

        if (mostrarFiltros) {
            AlertDialog(
                onDismissRequest = { mostrarFiltros = false },
                title = { Text(text = "Filtrar restaurantes") },
                text = {
                    FiltrosScreen(
                        onFiltrar = { tiposSeleccionados, radio, buscarUbicacion ->
                            filtrosSeleccionados = tiposSeleccionados
                            ratioBusqueda = radio
                            buscarPorUbicacion = buscarUbicacion
                            mostrarFiltros = false
                        },
                        onDismiss = { mostrarFiltros = false }
                    )
                },
                confirmButton = {}
            )
        }
    }
}


@Composable
fun rememberUserLocation(): LatLng? {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    Log.d(
                        "UserLocation",
                        "Ubicación obtenida: Lat: ${location.latitude}, Lng: ${location.longitude}"
                    )
                    userLocation = LatLng(location.latitude, location.longitude)
                } else {
                    Log.e("UserLocation", "No se pudo obtener la ubicación del usuario.")
                }
            }.addOnFailureListener { exception ->
                Log.e("UserLocation", "Error al obtener la ubicación: ${exception.message}")
            }
        } else {
            Log.e("UserLocation", "Permiso de ubicación no concedido.")
        }
    }
    return userLocation
}


suspend fun getLatLngFromAddress(context: Context, address: String): LatLng? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocationName(address, 1)

            if (addresses.isNullOrEmpty()) {
                Log.e("GEOCODING", "No se encontró ninguna dirección para $address")
                return@withContext null
            }
            val addressResult = addresses.firstOrNull()
            if (addressResult != null) {
                Log.d(
                    "GEOCODING",
                    "Dirección encontrada: Lat: ${addressResult.latitude}, Lon: ${addressResult.longitude}"
                )
                LatLng(addressResult.latitude, addressResult.longitude)
            } else {
                Log.e("GEOCODING", "No se pudo procesar la dirección correctamente.")
                null
            }
        } catch (e: IOException) {
            Log.e("GEOCODING", "Error de geocodificación: ${e.message}")
            null
        }
    }
}


fun calcularDistancia(userLatLng: LatLng, restaurantLatLng: LatLng): Float {
    val userLocation = Location("").apply {
        latitude = userLatLng.latitude
        longitude = userLatLng.longitude
    }
    val restaurantLocation = Location("").apply {
        latitude = restaurantLatLng.latitude
        longitude = restaurantLatLng.longitude
    }
    return userLocation.distanceTo(restaurantLocation) / 1000 // Devuelve la distancia en km
}
