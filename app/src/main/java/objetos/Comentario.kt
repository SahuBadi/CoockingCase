package objetos



class Comentario {
    var id: Int = 0
    var id_receta: Int = 0
    var comentario: String = ""
    var fecha: String = ""


    constructor(comentario:String, fecha:String){
        this.comentario = comentario
        this.fecha = fecha
    }
}