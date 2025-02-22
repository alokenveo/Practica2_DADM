package unex.cum.reservasgo_dadm.data.responses

import unex.cum.reservasgo_dadm.data.model.Notificacion
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.model.Usuario

data class ApiResponseRestaurantes(
    val error: Boolean,
    val mensaje:String,
    val restaurantes: List<Restaurante>?=null
)

data class RestauranteResponse(
    val error:Boolean,
    val mensaje:String,
    val restaurante:Restaurante?=null
)

data class RegisterResponse(
    val error: Boolean,
    val mensaje: String
)

data class LoginResponse(
    val error: Boolean,
    val mensaje: String,
    val usuario: Usuario?=null
)

data class ApiResponseReservas(
    val error: Boolean,
    val mensaje: String?,
    val reservas: List<Reserva>?=null
)

data class ReservaResponse(
    val error: Boolean,
    val mensaje: String
)

data class ApiResponseFavoritos(
    val error:Boolean,
    val mensaje: String?,
    val favoritos:List<Restaurante>?=null
)

data class FavoritoResponse(
    val error: Boolean,
    val mensaje: String
)

data class EsFavoritoResponse(
    val error:Boolean,
    val mensaje: String?,
    val esFavorito:Boolean
)

data class UsuarioResponse(
    val error:Boolean,
    val mensaje: String?,
    val usuario: Usuario?=null
)

data class ApiResponseNotificaciones(
    val error:Boolean,
    val mensaje: String?,
    val notificaciones:List<Notificacion>?=null
)

data class NotificacionResponse(
    val error: Boolean,
    val mensaje: String
)