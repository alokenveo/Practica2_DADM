package unex.cum.reservasgo_dadm.model

data class Usuario(
    val id:String,
    val nombre:String,
    val email:String,
    val password:String,
    val restaurantesFavoritos:List<String> = emptyList()
)
