package unex.cum.reservasgo_dadm.data.responses

import unex.cum.reservasgo_dadm.data.model.Restaurante

data class ApiResponseRestaurantes(
    val error: Boolean,
    val mensaje:String,
    val restaurantes: List<Restaurante>?=null
)
