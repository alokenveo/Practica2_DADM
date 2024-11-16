package unex.cum.reservasgo_dadm.view.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import unex.cum.reservasgo_dadm.R


@Composable
fun RestauranteCard(index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(2.dp, Color.Gray))
            .clickable(onClick = {})
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_restaurante),
            contentDescription = "Imagen del restaurante",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier= Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = "Restaurante ${index + 1}",
                    fontSize=18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text="Breve descripción del restaurante",
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = {}) {
                Icon(Icons.Outlined.Star, contentDescription = "Favoritos")
            }
        }
    }
}