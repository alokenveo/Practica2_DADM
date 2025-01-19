package unex.cum.reservasgo_dadm.data.repository

import android.util.Log
import unex.cum.reservasgo_dadm.data.model.Reserva
import unex.cum.reservasgo_dadm.data.model.Restaurante
import unex.cum.reservasgo_dadm.data.responses.LoginResponse
import unex.cum.reservasgo_dadm.data.responses.RegisterResponse
import unex.cum.reservasgo_dadm.network.ReservasGoAPI

class ReservasGoRepository(private val api: ReservasGoAPI) {
    suspend fun getRestaurantes(): List<Restaurante>{
        val response=api.getRestaurantes()
        return response.restaurantes?: emptyList()
    }

    suspend fun obtenerRestaurantePorId(id:Int):Restaurante{
        val response=api.obtenerRestaurantePorId(id)
        return response.restaurante!!
    }

    suspend fun registrarUsuario(nombre: String, email: String, password: String, direccion: String, telefono: String): RegisterResponse {
        return api.registrarUsuario(nombre = nombre, email = email, password = password, direccion = direccion, telefono = telefono)
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return api.loginUser(email = email, password = password)
    }

    suspend fun getReservas(idUsuario: Int): List<Reserva> {
        val response = api.verReservas(idUsuario)
        return response.reservas ?: emptyList()
    }
}

