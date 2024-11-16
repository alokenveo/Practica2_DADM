package unex.cum.reservasgo_dadm.model

data class Restaurante(
    val id_restaurante: Int,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val tipo_cocina: String,
    val descripcion: String,
    val foto: String,
    val rating_promedio: Float
)

