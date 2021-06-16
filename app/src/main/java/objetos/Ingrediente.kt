package objetos
import java.io.Serializable

class Ingrediente: Serializable {
    var id: Int = 0
    var id_receta: Int = 0
    var ingrediente: String = ""
    var cantidad: String = ""

    constructor(ingrediente:String, cantidad:String){
        this.ingrediente = ingrediente
        this.cantidad = cantidad
    }
}