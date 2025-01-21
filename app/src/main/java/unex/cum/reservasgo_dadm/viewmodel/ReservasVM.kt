package unex.cum.reservasgo_dadm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class ReservasVM(private val repository: ReservasGoRepository) : ViewModel() {
    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas

    private val _mensaje = MutableStateFlow<String>("")
    val mensaje: StateFlow<String> = _mensaje

    private val _restaurante = MutableStateFlow<Restaurante?>(null)
    val restaurante: StateFlow<Restaurante?> = _restaurante

    fun fetchReservas(idUsuario: Int) {
        viewModelScope.launch {
            try {
                _reservas.value = repository.getReservas(idUsuario)
                Log.d("CANTIDAD RESERVAS", "Cantidad de reservas obtenidas: ${_reservas.value.size}")
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar las reservas: ${e.message}"
            }
        }
    }

    fun obtenerRestaurante(idRestaurante: Int) {
        viewModelScope.launch {
            try {
                val restaurante = repository.obtenerRestaurantePorId(idRestaurante)
                _restaurante.value = restaurante
            } catch (e: Exception) {
                _mensaje.value = "Error al obtener el restaurante: ${e.message}"
            }
        }
    }
}
