package unex.cum.reservasgo_dadm.data.model

data class Usuario(
    val id_usuario: Int,
    val nombre: String,
    val email: String,
    val password: String,
    val foto_perfil: String,
    val fecha_registro: String,
    val telefono: String,
    val direccion: String
)

