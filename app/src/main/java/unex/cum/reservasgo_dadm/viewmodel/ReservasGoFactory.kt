package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import unex.cum.reservasgo_dadm.data.repository.ReservasGoRepository
import unex.cum.reservasgo_dadm.network.RetrofitClient

class MainVMFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository= ReservasGoRepository(RetrofitClient.api)
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

class LoginVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return LoginVM(repository) as T
    }
}

class ReservasVMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = ReservasGoRepository(RetrofitClient.api)
        return ReservasVM(repository) as T
    }
}
