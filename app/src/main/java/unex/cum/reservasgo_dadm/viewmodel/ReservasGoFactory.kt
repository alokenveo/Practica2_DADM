package unex.cum.reservasgo_dadm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.network.RetrofitClient
import unex.cum.reservasgo_dadm.network.SessionManager

class MainVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return MainVM(repository) as T
    }
}

class RegisterVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return RegisterVM(repository) as T
    }
}

class LoginVMFactory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return LoginVM(repository, sessionManager) as T
    }
}

class ReservasVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return ReservasVM(repository) as T
    }
}

class ReservaVMFactory(
    private val application: Application,
    private val notificacionesVM: NotificacionesVM
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return ReservaVM(repository, notificacionesVM, application) as T
    }
}

class FavoritosVMFactory(private val notificacionesVM: NotificacionesVM) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return FavoritosVM(repository, notificacionesVM) as T
    }
}

class UsuarioVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return UsuarioVM(repository) as T
    }
}

class NotificacionesVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return NotificacionesVM(repository) as T
    }
}
