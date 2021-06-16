package com.example.coockingcase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import objetos.Comentario
import objetos.Ingrediente
import objetos.Receta

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,1) {

    companion object{
        val DATABASE_NAME = "MIS_RECETAS"

        val TABLE_NAME = "RECETAS"
        val TABLE_NAME2 = "INGREDIENTES"
        val TABLE_NAME3  = "FOTOS"
        val TABLE_NAME4 = "COMENTARIOS"

        val COLUMN_NOMBRE = "Nombre"
        val COLUMN_DIFICULTAD = "Dificultad"
        val COLUMN_PREPARACION = "Preparacion"
        val COLUMN_TIEMPO = "Tiempo"
        val COLUMN_TEMPERATURA = "Temperatura"
        val COLUMN_INGREDIENTE = "Ingrediente"
        val COLUMN_CANTIDAD = "Cantidad"
        val COLUMN_FOTO = "Foto"
        val COLUMN_COMENTARIO = "Comentario"
        val COLUMN_FECHA = "Fecha"
        val COLUMN_ID = "Id"
        val COLUMN_ID_RECETA = "Id_Receta"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS RECETAS(Id integer primary key AUTOINCREMENT, Nombre TEXT NOT NULL, Dificultad TEXT, Preparacion TEXT, Tiempo TEXT, Temperatura TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS INGREDIENTES(Id integer primary key AUTOINCREMENT, Id_Receta integer, Ingrediente TEXT, Cantidad TEXT)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS FOTOS(Id integer primary key AUTOINCREMENT,Id_Receta integer, Foto BLOB)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS COMENTARIOS(Id integer primary key AUTOINCREMENT,Id_Receta integer, Comentario TEXT, Fecha TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME)
        db?.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME2)
        db?.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME3)
        db?.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME4)
        onCreate(db)
    }

    fun insertReceta(receta: Receta?): Int {
        var lastid = -1
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NOMBRE, receta?.nombre?.capitalize())
        cv.put(COLUMN_DIFICULTAD, receta?.dificultad?.capitalize())
        cv.put(COLUMN_PREPARACION, receta?.preparacion?.capitalize())
        cv.put(COLUMN_TIEMPO, receta?.tiempo)
        cv.put(COLUMN_TEMPERATURA, receta?.temperatura)
        var result = db.insert(TABLE_NAME, null, cv)
        if (result== -1.toLong())
            Toast.makeText(context, "Algo falló en Recetas", Toast.LENGTH_SHORT).show()
        else{
            //Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_SHORT).show()
            var rs = db.rawQuery("SELECT MAX(Id) lastid FROM RECETAS", null)
            if (rs.count > 0) {
                rs.moveToNext()
                lastid = rs.getInt(rs.getColumnIndex("lastid"))
            }
        }
        return lastid
    }

    fun insertIngrediente(ingrediente: Ingrediente){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ID_RECETA, ingrediente.id_receta)
        cv.put(COLUMN_INGREDIENTE, ingrediente.ingrediente.capitalize())
        cv.put(COLUMN_CANTIDAD, ingrediente.cantidad)
        var result = db.insert(TABLE_NAME2, null, cv)
        if (result== -1.toLong())
            Toast.makeText(context, "Algo falló en Ingredientes", Toast.LENGTH_SHORT).show()
        //else
            //Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_SHORT).show()
    }

    fun insertFoto(id_receta: Int, foto: ByteArray){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ID_RECETA, id_receta)
        cv.put(COLUMN_FOTO, foto)
        var result = db.insert(TABLE_NAME3, null, cv)
        if (result== -1.toLong())
            Toast.makeText(context, "Algo falló en Fotos", Toast.LENGTH_SHORT).show()
       // else
            //Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_SHORT).show()
    }

    fun insertComentario(id_receta: Int, comentario: Comentario?): Int {
        var idcomentario = -1
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ID_RECETA, id_receta)
        cv.put(COLUMN_COMENTARIO, comentario?.comentario)
        cv.put(COLUMN_FECHA, comentario?.fecha)
        var result = db.insert(TABLE_NAME4, null, cv)
        if(result== -1.toLong())
            Toast.makeText(context, "Algo falló en Comentarios", Toast.LENGTH_SHORT).show()
        else{
            //Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_SHORT).show()
            var rs = db.rawQuery("SELECT MAX(Id) idcomentario FROM COMENTARIOS", null)
            if (rs.count > 0) {
                rs.moveToNext()
                idcomentario = rs.getInt(rs.getColumnIndex("idcomentario"))
            }
        }
        return idcomentario
    }

    fun updateReceta(receta: Receta){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NOMBRE, receta.nombre.capitalize())
        cv.put(COLUMN_DIFICULTAD, receta.dificultad.capitalize())
        cv.put(COLUMN_PREPARACION, receta.preparacion.capitalize())
        cv.put(COLUMN_TIEMPO, receta.tiempo)
        cv.put(COLUMN_TEMPERATURA, receta.temperatura)
        db.update(TABLE_NAME, cv, "id = " + receta.id.toString(), arrayOf())// necesita String, porqué? no puedo poner id
    }

    fun updateIngrediente(ingrediente: Ingrediente){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_INGREDIENTE, ingrediente.ingrediente.capitalize())
        cv.put(COLUMN_CANTIDAD, ingrediente.cantidad)
        db.update(TABLE_NAME2, cv,"id_receta = ?", arrayOf())
    }

    fun updateComentario(comentario: Comentario){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_COMENTARIO, comentario.comentario.capitalize())
        db.update(TABLE_NAME4, cv, "id_receta = " + comentario.id_receta.toString(), arrayOf())
    }

    fun deleteRecetaCompleta(idreceta: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, COLUMN_ID+"="+idreceta.toString(),null)
        db.delete(TABLE_NAME2, COLUMN_ID_RECETA+"="+idreceta.toString(),null)
        db.delete(TABLE_NAME3, COLUMN_ID_RECETA+"="+idreceta.toString(),null)
        db.delete(TABLE_NAME4, COLUMN_ID_RECETA+"="+idreceta.toString(),null)
        cerrarDB()
    }


    fun getRecetas() : Cursor {
        var db = readableDatabase
        var cursor = db.rawQuery("select * from recetas, fotos where fotos.Id_Receta = recetas.Id and fotos.id = (select min(fotos.Id) from fotos where fotos.Id_Receta = recetas.id) order by Nombre", null)
        return cursor
    }


    fun cerrarDB(){
        val db= writableDatabase
        db.close()
    }


}

