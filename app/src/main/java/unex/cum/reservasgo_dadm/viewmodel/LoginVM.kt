package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.network.SessionManager

class LoginVM(private val repository: ReservasGoRepository, private val sessionManager: SessionManager) : ViewModel() {
    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> = _loginResult

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                if (response.usuario != null) {
                    sessionManager.saveUserId(response.usuario.id_usuario)
                    _loginResult.value = "success"
                } else {
                    _loginResult.value = response.mensaje
                }
            } catch (e: Exception) {
                _loginResult.value = "Error de conexión: ${e.message}"
            }
        }
    }
}

