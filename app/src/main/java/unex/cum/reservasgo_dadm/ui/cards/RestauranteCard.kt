package unex.cum.reservasgo_dadm.ui.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import unex.cum.reservasgo_dadm.R
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.viewmodel.FavoritosVM
import unex.cum.reservasgo_dadm.viewmodel.FavoritosVMFactory
import unex.cum.reservasgo_dadm.viewmodel.NotificacionesVM
import unex.cum.reservasgo_dadm.viewmodel.NotificacionesVMFactory
import unex.cum.reservasgo_dadm.viewmodel.ReservaVM
import unex.cum.reservasgo_dadm.viewmodel.ReservaVMFactory


@Composable
fun RestauranteCard(
    restaurante: Restaurante,
    navController: NavHostController,
    usuarioId: Int
) {
    val notificacionesVM: NotificacionesVM = viewModel(factory = NotificacionesVMFactory())
    val favoritosVM: FavoritosVM = viewModel(factory = FavoritosVMFactory(notificacionesVM))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(2.dp, Color.Gray))
            .clickable(onClick = {
                navController.navigate("restauranteScreen/${restaurante.id_restaurante}")
            })
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = restaurante.foto
            ),
            contentDescription = "Imagen del restaurante",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = restaurante.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = restaurante.descripcion,
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = {
                favoritosVM.agregarFavorito(usuarioId, restaurante.id_restaurante)
            }) {
                Icon(
                    Icons.Default.StarBorder,
                    contentDescription = "Favoritos"
                )
            }
        }
    }
}