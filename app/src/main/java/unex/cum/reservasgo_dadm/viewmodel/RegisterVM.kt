package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class RegisterVM(private val repository: ReservasGoRepository) : ViewModel() {
    private val _registerResult = MutableStateFlow<String?>(null)
    val registerResult: StateFlow<String?> = _registerResult

    fun registrarUsuario(nombre: String, email: String, password: String, direccion: String, telefono: String) {
        viewModelScope.launch {
            try {
                val response = repository.registrarUsuario(nombre, email, password, direccion, telefono)
                _registerResult.value = response.mensaje
            } catch (e: Exception) {
                _registerResult.value = "Error: ${e.message}"
            }
        }
    }
}