package unex.cum.reservasgo_dadm.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.repository.RestaurantesRepository

class MainVM(private val repository: RestaurantesRepository): ViewModel() {
    private val _restaurantes = MutableStateFlow<List<Restaurante>>(emptyList())
    val restaurantes: StateFlow<List<Restaurante>> = _restaurantes

    private val _message=MutableStateFlow<String>("")
    val message:StateFlow<String> = _message

    init {
        fetchRestaurantes()
    }

    fun fetchRestaurantes() {
        viewModelScope.launch {
            try {
                _restaurantes.value = repository.getRestaurantes()
                Log.d("MainVM", "Restaurantes cargados: $restaurantes")
            } catch (e: Exception) {
                _message.value = "Error al cargar los heroes: ${e.message}"
            }
        }
    }

}
