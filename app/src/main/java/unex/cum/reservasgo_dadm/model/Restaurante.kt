package unex.cum.reservasgo_dadm.model

data class Restaurante(
    val id: String,
    val nombre: String,
    val ubicacion: String,
    val cocina: String,
    val esFavorito: Boolean = false
)
