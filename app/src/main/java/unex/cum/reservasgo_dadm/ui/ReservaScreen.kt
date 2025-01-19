package unex.cum.reservasgo_dadm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalContext
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.ui.theme.colorApp
import java.util.Calendar

@Composable
fun ReservaScreen() {
    // Estados para manejar la entrada de datos
    var nombre by remember { mutableStateOf("") }
    var cantidadComensales by remember { mutableStateOf(1) }
    var fechaSeleccionada by remember { mutableStateOf("") }
    var horaSeleccionada by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Para mostrar el DatePicker y TimePicker
    val mostrarDatePicker = remember { mutableStateOf(false) }
    val mostrarTimePicker = remember { mutableStateOf(false) }

    // Función para mostrar el DatePicker
    if (mostrarDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                mostrarDatePicker.value = false // Ocultar el DatePicker
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Función para mostrar el TimePicker
    if (mostrarTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                horaSeleccionada = "$hourOfDay:$minute"
                mostrarTimePicker.value = false // Ocultar el TimePicker
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaciado entre elementos
    ) {
        // Campo de nombre
        Text("Nombre", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Escribe tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la cantidad de comensales
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cantidad de comensales", style = MaterialTheme.typography.bodyLarge)
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconButton(onClick = { if (cantidadComensales > 1) cantidadComensales-- }) {
                    Icon(Icons.Default.Remove, contentDescription = "Disminuir")
                }

                Text(cantidadComensales.toString(), style = MaterialTheme.typography.bodyLarge)

                IconButton(onClick = { cantidadComensales++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar")
                }
            }
        }


        // Selección de fecha
        Text("Seleccionar fecha", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = { mostrarDatePicker.value = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorApp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (fechaSeleccionada.isNotEmpty()) fechaSeleccionada else "Seleccionar fecha")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Seleccionar fecha")
            }
        }

        // Selección de hora
        Text("Seleccionar hora", style = MaterialTheme.typography.titleMedium)
        Button(
            onClick = { mostrarTimePicker.value = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorApp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (horaSeleccionada.isNotEmpty()) horaSeleccionada else "Seleccionar hora")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Seleccionar hora")
            }
        }
    }
}


@Composable
fun InfoReservaScreen(reserva: Reserva, nombreUsuario: String, nombreRestaurante: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Reserva de: $nombreUsuario", style = MaterialTheme.typography.titleMedium)
        Text(text = "Restaurante: $nombreRestaurante", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Fecha: ${reserva.fecha_reserva}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Personas: ${reserva.numero_personas}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Estado: ${reserva.estado_reserva}", style = MaterialTheme.typography.bodyMedium)
    }
}