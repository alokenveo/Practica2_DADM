package unex.cum.reservasgo_dadm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.workDataOf
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequestBuilder
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.NotificacionesWorker
import unex.cum.reservasgo_dadm.RecordatorioWorker
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.data.responses.ReservaResponse
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ReservaVM(
    private val repository: ReservasGoRepository,
    private val notificacionesVM: NotificacionesVM,
    application: Application
) : AndroidViewModel(application) {

    private val _hacerReservaResponse =
        MutableStateFlow<ReservaResponse>(ReservaResponse(error = false, mensaje = ""))
    val hacerReservaResponse: StateFlow<ReservaResponse> get() = _hacerReservaResponse

    fun hacerReserva(
        idUsuario: Int,
        idRestaurante: Int,
        fechaReserva: String,
        numeroPersonas: Int,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val response =
                    repository.hacerReserva(idUsuario, idRestaurante, fechaReserva, numeroPersonas)
                val restaurante=repository.obtenerRestaurantePorId(idRestaurante)
                notificacionesVM.crearNotificacion(idUsuario, "reserva", restaurante.nombre)

                val workRequest = OneTimeWorkRequestBuilder<NotificacionesWorker>()
                    .setInputData(workDataOf("mensaje" to "Tu reserva en ${restaurante.nombre} ha sido confirmada."))
                    .build()

                WorkManager.getInstance(context).enqueue(workRequest)

                programarRecordatorios(fechaReserva)

                _hacerReservaResponse.value = response
            } catch (e: Exception) {
                _hacerReservaResponse.value =
                    ReservaResponse(error = true, mensaje = "Error al realizar la reserva")
            }
        }
    }

    fun programarRecordatorios(fechaReserva: String) {
        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaReservaDate = formato.parse(fechaReserva) ?: return

        val tiemposRecordatorios = listOf(
            Pair("Falta 1 hora para tu reserva en ", TimeUnit.HOURS.toMillis(1)),
            Pair("Mañana tienes una reserva en ", TimeUnit.DAYS.toMillis(1)),
            Pair("En 3 días tienes una reserva en ", TimeUnit.DAYS.toMillis(3))
        )

        tiemposRecordatorios.forEach { (mensajeBase, tiempoAntes) ->
            val tiempoNotificacion = fechaReservaDate.time - tiempoAntes
            if (tiempoNotificacion > System.currentTimeMillis()) {
                val mensaje = "$mensajeBase ${fechaReserva}"
                programarWorker(mensaje, tiempoNotificacion)
            }
        }
    }

    private fun programarWorker(mensaje: String, tiempoNotificacion: Long) {
        val workRequest = OneTimeWorkRequestBuilder<RecordatorioWorker>()
            .setInitialDelay(tiempoNotificacion - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("mensaje" to mensaje))
            .build()

        WorkManager.getInstance(getApplication()).enqueue(workRequest)
    }
}


