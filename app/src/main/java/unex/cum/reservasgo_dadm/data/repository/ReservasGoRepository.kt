package unex.cum.reservasgo_dadm.data.repository

import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.network.RestaurantesAPI

class RestaurantesRepository(private val api: RestaurantesAPI) {
    suspend fun getRestaurantes(): List<Restaurante>{
        val response=api.getRestaurantes()
        return response.restaurantes?: emptyList()
    }

    suspend fun obtenerRestaurantePorId(id:Int):Restaurante{
        return api.obtenerRestaurantePorId(id)
    }
}