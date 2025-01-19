package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class ReservasVM(private val repository: ReservasGoRepository) : ViewModel() {
    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas

    private val _mensaje = MutableStateFlow<String>("")
    val mensaje: StateFlow<String> = _mensaje

    fun fetchReservas(idUsuario: Int) {
        viewModelScope.launch {
            try {
                _reservas.value = repository.getReservas(idUsuario)
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar las reservas: ${e.message}"
            }
        }
    }
}
