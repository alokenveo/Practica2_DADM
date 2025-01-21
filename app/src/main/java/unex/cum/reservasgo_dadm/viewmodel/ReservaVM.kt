package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.data.responses.ReservaResponse

class ReservaVM(
    private val repository: ReservasGoRepository,
    notificacionesVM: NotificacionesVM= viewModel(factory=NotificacionesVMFactory())
) :
    ViewModel() {

    private val _hacerReservaResponse =
        MutableStateFlow<ReservaResponse>(ReservaResponse(error = false, mensaje = ""))
    val hacerReservaResponse: StateFlow<ReservaResponse> get() = _hacerReservaResponse

    fun hacerReserva(
        idUsuario: Int,
        idRestaurante: Int,
        fechaReserva: String,
        numeroPersonas: Int
    ) {
        viewModelScope.launch {
            try {
                val response =
                    repository.hacerReserva(idUsuario, idRestaurante, fechaReserva, numeroPersonas)
                val restaurante=repository.obtenerRestaurantePorId(idRestaurante)
                notificacionesVM.crearNotificacion(idUsuario, "reserva", restaurante.nombre)
                _hacerReservaResponse.value = response
            } catch (e: Exception) {
                _hacerReservaResponse.value =
                    ReservaResponse(error = true, mensaje = "Error al realizar la reserva")
            }
        }
    }
}

