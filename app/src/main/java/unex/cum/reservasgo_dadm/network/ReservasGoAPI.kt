package unex.cum.reservasgo_dadm.network

import retrofit2.http.GET
import retrofit2.http.Query
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.responses.ApiResponseRestaurantes

interface RestaurantesAPI {
    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerRestaurantes")
    suspend fun getRestaurantes(): ApiResponseRestaurantes

    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerRestaurantePorId")
    suspend fun obtenerRestaurantePorId(@Query("id") id: Int): Restaurante

}
