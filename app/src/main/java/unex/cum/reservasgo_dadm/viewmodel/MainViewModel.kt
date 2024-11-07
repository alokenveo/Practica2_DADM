package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.model.Restaurante

class MainViewModel : ViewModel() {
    private val _restaurantes = MutableStateFlow<List<Restaurante>>(emptyList())
    val restaurantes: StateFlow<List<Restaurante>> get() = _restaurantes

    fun cargarRestaurantes() {
        viewModelScope.launch {
            // Aquí iría la lógica para cargar restaurantes
            _restaurantes.value = listOf(
                Restaurante("1", "Restaurante A", "Madrid", "Italiana"),
                Restaurante("2", "Restaurante B", "Barcelona", "Japonesa")
            )
        }
    }
}
