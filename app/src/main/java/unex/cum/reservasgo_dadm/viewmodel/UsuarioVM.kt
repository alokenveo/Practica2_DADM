package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import unex.cum.reservasgo_dadm.data.model.Usuario
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository

class UsuarioVM(private val repository: ReservasGoRepository):ViewModel() {

    private val _usuario= MutableStateFlow<Usuario?>(null)
    val usuario:StateFlow<Usuario?> = _usuario

    private val _mensaje = MutableStateFlow<String>("")
    val mensaje: StateFlow<String> = _mensaje

    fun getUsuario(id: Int) {
        viewModelScope.launch {
            try{
                val usuario = repository.getUsuario(id)
                _usuario.value = usuario
            }
            catch(e:Exception){
                _mensaje.value = "Error al obtener el usuario: ${e.message}"
            }
        }
    }
}