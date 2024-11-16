package unex.cum.reservasgo_dadm.model

data class Favorito(
    val id_favorito: Int,
    val id_usuario: Int,
    val id_restaurante: Int,
    val fecha_agregado: String
)

