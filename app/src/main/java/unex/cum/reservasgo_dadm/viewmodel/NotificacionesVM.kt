package unex.cum.reservasgo_dadm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Notificacion
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class NotificacionesVM(private val repository:ReservasGoRepository):ViewModel() {

    private val _notificaciones= MutableStateFlow<List<Notificacion>>(emptyList())
    val notificaciones: StateFlow<List<Notificacion>> = _notificaciones

    private val _mensaje = MutableStateFlow<String>("")
    val mensaje: StateFlow<String> = _mensaje

    private val _notificacionResult = MutableStateFlow<String?>(null)
    val notificacionResult: StateFlow<String?> = _notificacionResult

    fun obtenerNotificaciones(usuarioId:Int){
        viewModelScope.launch {
            try{
                _notificaciones.value=repository.getNotificaciones(usuarioId)
            }
            catch(e:Exception){
                _mensaje.value="Error al obtener notificaciones: ${e.message}"
            }
        }
    }

    fun crearNotificacion(idUsuario: Int, tipo: String, datoAdicional: String) {
        val mensajeNoti = when (tipo) {
            "reserva" -> "Tu reserva en $datoAdicional ha sido confirmada."
            "favorito" -> "Has agregado $datoAdicional a tus favoritos."
            else -> "Tienes una nueva notificación."
        }

        Log.d("NotificacionesVM", "Llamando a crearNotificacion con mensaje: $mensajeNoti")

        viewModelScope.launch {
            try {
                repository.crearNotificacion(idUsuario, mensajeNoti)
                Log.d("NotificacionesVM", "Notificación creada exitosamente")
            } catch (e: Exception) {
                Log.e("NotificacionesVM", "Error al crear notificación: ${e.message}")
                _mensaje.value="Error al obtener notificaciones: ${e.message}"
            }
        }
    }
}