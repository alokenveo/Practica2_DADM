package unex.cum.reservasgo_dadm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class FavoritosVM(
    private val repository: ReservasGoRepository,
    private val notificacionesVM: NotificacionesVM
) : ViewModel() {
    private val _favoritoResult = MutableStateFlow<String?>(null)
    val favoritoResult: StateFlow<String?> = _favoritoResult

    private val _favoritos = MutableStateFlow<List<Restaurante>>(emptyList())
    val favoritos: StateFlow<List<Restaurante>> = _favoritos

    private val _mensaje = MutableStateFlow<String>("")
    val mensaje: StateFlow<String> = _mensaje

    private val _esFavoritoResult = MutableStateFlow<Boolean>(false)
    val esFavoritoResult: StateFlow<Boolean> = _esFavoritoResult

    fun agregarFavorito(idUsuario: Int, idRestaurante: Int) {
        viewModelScope.launch {
            try {
                val response = repository.agregarFavorito(idUsuario, idRestaurante)
                val restaurante = repository.obtenerRestaurantePorId(idRestaurante)
                Log.d("AGREGANDO FAVORITO","El favorito es: ${restaurante.nombre}")
                notificacionesVM.crearNotificacion(idUsuario, "favorito", restaurante.nombre)
                _favoritoResult.value = response.mensaje
                _esFavoritoResult.value = true
            } catch (e: Exception) {
                _mensaje.value = "Error al agregar favorito: ${e.message}"
            }
        }
    }

    fun eliminarFavorito(idUsuario: Int, idRestaurante: Int) {
        viewModelScope.launch {
            try {
                val response = repository.eliminarFavorito(idUsuario, idRestaurante)
                _favoritoResult.value = response.mensaje
                _esFavoritoResult.value = false
            } catch (e: Exception) {
                _mensaje.value = "Error al agregar favorito: ${e.message}"
            }
        }
    }

    fun esFavorito(idUsuario: Int, idRestaurante: Int) {
        viewModelScope.launch {
            try {
                _esFavoritoResult.value = repository.esFavorito(idUsuario, idRestaurante)
            } catch (e: Exception) {
                _mensaje.value = "Error al agregar favorito: ${e.message}"
            }
        }
    }

    fun obtenerFavoritos(idUsuario: Int) {
        viewModelScope.launch {
            try {
                _favoritos.value = repository.obtenerFavoritos(idUsuario)
            } catch (e: Exception) {
                _mensaje.value = "Error al agregar favorito: ${e.message}"
            }
        }
    }
}