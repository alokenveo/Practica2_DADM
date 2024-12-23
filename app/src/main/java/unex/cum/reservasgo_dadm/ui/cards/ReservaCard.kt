package unex.cum.reservasgo_dadm.ui.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import unex.cum.reservasgo_dadm.R

@Composable
fun ReservaCard(index: Int, onReservaClick:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start=16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 8.dp
            )
            .border(BorderStroke(2.dp, Color.Gray))
            .clickable(onClick = onReservaClick)
    ) {
        //TODO añadir contenido
        Image(
            painter = painterResource(id = R.drawable.ic_restaurante),
            contentDescription = "Logo de la app",
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
        Column() {
            Text(
                text = "Reserva ${index + 1}",
                fontSize=18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text="Breve descripción de la reserva",
                fontSize = 12.sp
            )
        }
    }
}