package unex.cum.reservasgo_dadm.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.ui.Alignment
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.data.model.Usuario
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import unex.cum.reservasgo_dadm.viewmodel.UsuarioVM
import unex.cum.reservasgo_dadm.viewmodel.UsuarioVMFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioScreen(
    navController: NavHostController,
    usuarioId:Int,
    usuarioVM: UsuarioVM = viewModel(factory = UsuarioVMFactory())
) {
    val usuario by usuarioVM.usuario.collectAsState()

    LaunchedEffect(Unit) {
        usuarioVM.getUsuario(usuarioId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Panel de Usuario")
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
                    IconButton(onClick = {}) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Usuario",
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Nombre completo", fontSize = 12.sp)
                    TextField(value = usuario?.nombre ?: "", onValueChange = {}, enabled = false)

                    Text(text = "Correo Electrónico", fontSize = 12.sp)
                    TextField(value = usuario?.email ?: "", onValueChange = {}, enabled = false)

                    Text(text = "Dirección", fontSize = 12.sp)
                    TextField(value = usuario?.direccion ?: "", onValueChange = {}, enabled = false)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable(onClick = {
                            navController.navigate("favoritosScreen")
                        })
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.StarHalf,
                            contentDescription = "Lista",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "Favoritos")
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Flecha a la derecha"
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { /* Acción para reservar */ },
                        modifier = Modifier
                            .padding(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                    ) {
                        Text(text = "Cerrar sesión")
                    }
                }
            }
        }
    }
}