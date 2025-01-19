package unex.cum.reservasgo_dadm.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.responses.ApiResponseReservas
import unex.cum.reservasgo_dadm.data.responses.ApiResponseRestaurantes
import unex.cum.reservasgo_dadm.data.responses.LoginResponse
import unex.cum.reservasgo_dadm.data.responses.RegisterResponse
import unex.cum.reservasgo_dadm.data.responses.RestauranteResponse

interface ReservasGoAPI {
    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerRestaurantes")
    suspend fun getRestaurantes(): ApiResponseRestaurantes

    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerRestaurantePorId")
    suspend fun obtenerRestaurantePorId(@Query("id") id: Int): RestauranteResponse

    @FormUrlEncoded
    @POST("reservasGo/ReservasGoAPI/v1/?op=registrarUsuario")
    suspend fun registrarUsuario(
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("direccion") direccion: String,
        @Field("telefono") telefono: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("reservasGo/ReservasGoAPI/v1/?op=obtenerUsuario")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("reservasGo/ReservasGoAPI/v1/?op=verReservas")
    suspend fun verReservas(@Query("id_usuario") idUsuario: Int): ApiResponseReservas
}


