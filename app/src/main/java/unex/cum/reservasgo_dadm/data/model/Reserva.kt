package unex.cum.reservasgo_dadm.data.model

data class Reserva(
    val id_reserva: Int,
    val id_usuario: Int,
    val id_restaurante: Int,
    val fecha_reserva: String,
    val numero_personas: Int
)

