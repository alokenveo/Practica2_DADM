package unex.cum.reservasgo_dadm.model

data class Notificacion(
    val id_notificacion: Int,
    val id_usuario: Int,
    val mensaje: String,
    val fecha_envio: String,
    val estado: String
)

