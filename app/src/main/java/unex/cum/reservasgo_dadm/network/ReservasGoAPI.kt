package unex.cum.reservasgo_dadm.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.responses.ApiResponseFavoritos
import unex.cum.reservasgo_dadm.data.responses.ApiResponseNotificaciones
import unex.cum.reservasgo_dadm.data.responses.ApiResponseReservas
import unex.cum.reservasgo_dadm.data.responses.ApiResponseRestaurantes
import unex.cum.reservasgo_dadm.data.responses.EsFavoritoResponse
import unex.cum.reservasgo_dadm.data.responses.FavoritoResponse
import unex.cum.reservasgo_dadm.data.responses.LoginResponse
import unex.cum.reservasgo_dadm.data.responses.NotificacionResponse
import unex.cum.reservasgo_dadm.data.responses.RegisterResponse
import unex.cum.reservasgo_dadm.data.responses.ReservaResponse
import unex.cum.reservasgo_dadm.data.responses.RestauranteResponse
import unex.cum.reservasgo_dadm.data.responses.UsuarioResponse

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

    @FormUrlEncoded
    @POST("reservasGo/ReservasGoAPI/v1/?op=hacerReserva")
    suspend fun hacerReserva(
        @Field("id_usuario") idUsuario: Int,
        @Field("id_restaurante") idRestaurante: Int,
        @Field("fecha_reserva") fechaReserva: String,
        @Field("numero_personas") numeroPersonas: Int
    ): ReservaResponse

    @FormUrlEncoded
    @POST("/reservasGo/ReservasGoAPI/v1/?op=agregarFavorito")
    suspend fun agregarFavorito(
        @Field("id_usuario") idUsuario: Int,
        @Field("id_restaurante") idRestaurante: Int
    ): FavoritoResponse

    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerFavoritos")
    suspend fun obtenerFavoritos(@Query("id_usuario") idUsuario: Int): ApiResponseFavoritos

    @FormUrlEncoded
    @POST("/reservasGo/ReservasGoAPI/v1/?op=eliminarDeFavoritos")
    suspend fun eliminarFavorito(
        @Field("id_usuario") idUsuario: Int,
        @Field("id_restaurante") idRestaurante: Int
    ): FavoritoResponse

    @GET("reservasGo/ReservasGoAPI/v1/?op=esFavorito")
    suspend fun esFavorito(
        @Query("id_usuario") idUsuario: Int,
        @Query("id_restaurante") idRestaurante: Int
    ): EsFavoritoResponse

    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerUsuarioPorId")
    suspend fun obtenerUsuarioPorId(@Query("id_usuario") idUsuario: Int): UsuarioResponse

    @GET("reservasGo/ReservasGoAPI/v1/?op=obtenerNotificaciones")
    suspend fun obtenerNotificaciones(@Query("id_usuario") idUsuario: Int):ApiResponseNotificaciones

    @FormUrlEncoded
    @POST("reservasGo/ReservasGoAPI/v1/?op=crearNotificacion")
    suspend fun crearNotificacion(
        @Field("id_usuario") idUsuario: Int,
        @Field("mensaje") mensaje: String
    ): NotificacionResponse
}


