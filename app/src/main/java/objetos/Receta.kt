package objetos
import java.io.Serializable

class Receta : Serializable{
    var id : Int = 0
    var nombre : String = ""
    var dificultad : String = ""
    var preparacion : String = ""
    var tiempo : String = ""
    var temperatura : String = ""

    constructor(nombre:String, dificultad:String, preparacion:String = "", tiempo:String = "", temperatura:String = ""){
        this.nombre = nombre
        this.dificultad = dificultad
        this.preparacion = preparacion
        this.tiempo = tiempo
        this.temperatura = temperatura
    }
}