package unex.cum.reservasgo_dadm.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.data.model.Usuario
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.network.RetrofitClient
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import unex.cum.reservasgo_dadm.ui.cards.ReservaCard
import unex.cum.reservasgo_dadm.viewmodel.ReservasVM
import unex.cum.reservasgo_dadm.viewmodel.UsuarioVM
import unex.cum.reservasgo_dadm.viewmodel.UsuarioVMFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservasScreen(navController: NavHostController, reservasVM: ReservasVM, idUsuario: Int) {
    val reservas by reservasVM.reservas.collectAsState()
    val mensaje by reservasVM.mensaje.collectAsState()
    val restaurante by reservasVM.restaurante.collectAsState()

    var reservaSeleccionada by remember { mutableStateOf<Reserva?>(null) }
    var usuario by remember { mutableStateOf<Usuario?>(null) }

    LaunchedEffect(Unit) {
        val repo = ReservasGoRepository(RetrofitClient.api)
        usuario = repo.getUsuario(idUsuario)
        reservasVM.fetchReservas(idUsuario)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reservas")
                },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "Logo de la app",
                            modifier = Modifier.height(45.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Buscar",
                            modifier = Modifier.size(28.dp)
                        )
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
                    IconButton(onClick = {}) {
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
        if (reservas.isEmpty() && mensaje.isNotEmpty()) {
            Text(text = mensaje, modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                items(reservas) { reserva ->
                    LaunchedEffect(reserva.id_restaurante) {
                        reservasVM.obtenerRestaurante(reserva.id_restaurante)
                        Log.d(
                            "CARGA RESTAURANTE",
                            "Restaurante con ID ${reserva.id_restaurante} cargado"
                        )
                    }

                    // Espera a que el restaurante esté cargado
                    restaurante?.let {
                        Log.d("RESTAURANTE CARGADO", "Restaurante: ${it.nombre}")
                        ReservaCard(
                            reserva = reserva,
                            nombreRestaurante = it.nombre,
                            onReservaClick = { reservaSeleccionada = reserva }
                        )
                    }
                }
            }
        }

        reservaSeleccionada?.let { reserva ->
            LaunchedEffect(reserva.id_restaurante) {

                reservasVM.obtenerRestaurante(reserva.id_restaurante)
            }

            AlertDialog(
                onDismissRequest = { reservaSeleccionada = null },
                title = { Text("Detalles de la Reserva") },
                text = {
                    restaurante?.let {
                        InfoReservaScreen(
                            reserva = reserva,
                            nombreUsuario = usuario?.nombre ?: "Señor/a X",
                            nombreRestaurante = it.nombre
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { reservaSeleccionada = null },
                        colors = ButtonDefaults.buttonColors(containerColor = colorApp)
                    ) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
}