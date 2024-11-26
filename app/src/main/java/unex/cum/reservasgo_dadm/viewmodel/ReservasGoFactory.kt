package unex.cum.reservasgo_dadm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import unex.cum.reservasgo_dadm.data.repository.RestaurantesRepository
import unex.cum.reservasgo_dadm.network.RetrofitClient

class MainVMFactory: ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository= RestaurantesRepository(RetrofitClient.api)
        return MainVM(repository) as T
    }
}