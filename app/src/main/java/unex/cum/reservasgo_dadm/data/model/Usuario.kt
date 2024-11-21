package unex.cum.reservasgo_dadm.data.model

data class Usuario(
    val id_usuario: Int,
    val nombre: String,
    val correo_electronico: String,
    val contraseña: String,
    val foto_perfil: String,
    val fecha_registro: String,
    val telefono: String,
    val direccion: String
)

