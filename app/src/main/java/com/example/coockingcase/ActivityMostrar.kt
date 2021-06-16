package com.example.coockingcase

import Adapters.MIngredientesAdapter
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coockingcase.databinding.ActivityMostrarBinding
import objetos.Comentario
import objetos.Receta
import kotlinx.android.synthetic.main.activity_mostrar.*


class ActivityMostrar : AppCompatActivity() {

    private lateinit var binding: ActivityMostrarBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase
    private var lastid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMostrarBinding.inflate(layoutInflater)
        lastid = intent.getIntExtra("lastid", lastid)
        setContentView(binding.root)


        slider()
        setValorsReceta()
        setValorsIngrediente()
        setValorsComentario()

        binding.btnModificar.setOnClickListener {
            etEditable()
            btnGuardarMostrar.isEnabled = true
        }
        binding.btnComentario.setOnClickListener {
            var intent = Intent(this, ActivityComentario::class.java)
            intent.putExtra("lastid", lastid)
            startActivity(intent)
        }
        binding.ibFoto.setOnClickListener {
            var intent = Intent(this, ActivityFoto::class.java)
            intent.putExtra("lastid", lastid)
            startActivity(intent)
        }
        binding.btnGuardarMostrar.setOnClickListener {
            guardarCambios()
            etNoEditable()
        }
        binding.btnSalirMostrar.setOnClickListener {
            var intent = Intent(this, ActivityRecetas::class.java)
            startActivity(intent)
        }
    }

    private fun setValorsReceta(){
        var dbHelper = DBHelper(applicationContext)
        var db = dbHelper.readableDatabase
        val params = arrayOfNulls<String>(1)
        params[0] = lastid.toString()
        var rs = db.rawQuery("SELECT * FROM RECETAS WHERE ID=?", params)
        rs.moveToNext()
        etNombreReceta.setText(rs.getString(1))
        etDificultadReceta.setText(rs.getString(2))
        etmPreparacionReceta.setText(rs.getString(3))
        etTiempoReceta.setText(rs.getString(4))
        etTemperaturaReceta.setText(rs.getString(5))
    }
    private fun setValorsIngrediente(){
        dbHelper = DBHelper(this)
        db = dbHelper.readableDatabase
        val params = arrayOfNulls<String>(1)
        params[0] = lastid.toString()
        var rs = db.rawQuery("SELECT * FROM INGREDIENTES WHERE ID_RECETA=?", params)
        rs.moveToNext()
        val adaptador = MIngredientesAdapter()
        adaptador.MIngredientesAdapter(this, rs)
        binding.rvIngredientesReceta.setHasFixedSize(true)
        binding.rvIngredientesReceta.layoutManager = LinearLayoutManager(this)
        binding.rvIngredientesReceta.adapter = adaptador
    }

    private fun setValorsFoto() : ArrayList<Bitmap> {
        var fotos = ArrayList<Bitmap>()
        var dbHelper = DBHelper(applicationContext)
        var db = dbHelper.readableDatabase
        val params = arrayOfNulls<String>(1)
        params[0] = lastid.toString()
        var rs = db.rawQuery("SELECT * FROM FOTOS WHERE ID_RECETA=?", params)
        for (i in 1..rs.count) {
            rs.moveToNext()
            fotos.add(obtenerImagen(rs.getBlob(2)))
        }
        return fotos
    }

    private fun setValorsComentario(){
        var dbHelper = DBHelper(applicationContext)
        var db = dbHelper.readableDatabase
        val params = arrayOfNulls<String>(1)
        params[0] = lastid.toString()
        var rs = db.rawQuery("SELECT*FROM COMENTARIOS WHERE ID_RECETA=?", params)
        if (rs.count > 0) {
            rs.moveToNext()
            etmComentarioReceta.setText(rs.getString(2))
            etFechaReceta.setText(rs.getString(3))
        }
    }

    fun obtenerImagen(foto: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(foto, 0, foto.size)
    }

    private fun slider(){
        for (photo in setValorsFoto()) {
            val image = ImageView(applicationContext)
            image.scaleType = ImageView.ScaleType.FIT_XY
            image.setImageBitmap(photo)
            binding.ifFotos.addView(image)
        }
        binding.ifFotos.flipInterval = 5000
        binding.ifFotos.startFlipping()
    }

    private fun guardarCambios(){
        var receta = Receta(
                etNombreReceta.text.toString(),
                etDificultadReceta.text.toString(),
                etmPreparacionReceta.text.toString(),
                etTiempoReceta.text.toString(),
                etTemperaturaReceta.text.toString()
        )
        receta.id = lastid
        var comentario = Comentario(
                etmComentarioReceta.text.toString(),
                etFechaReceta.text.toString()
        )
        comentario.id_receta = lastid
        comentario.id = 1
        dbHelper.updateReceta(receta)
        dbHelper.updateComentario(comentario)
        Toast.makeText(this,"Actualizado",Toast.LENGTH_SHORT).show()
    }

    private fun etEditable(){
        etNombreReceta.isEnabled = true
        etNombreReceta.isFocusable = true
        etNombreReceta.isFocusableInTouchMode = true

        etDificultadReceta.isEnabled = true
        etDificultadReceta.isFocusable = true
        etDificultadReceta.isFocusableInTouchMode = true

        etmPreparacionReceta.isEnabled = true
        etmPreparacionReceta.isFocusable = true
        etmPreparacionReceta.isFocusableInTouchMode = true

        etTiempoReceta.isEnabled = true
        etTiempoReceta.isFocusable = true
        etTiempoReceta.isFocusableInTouchMode = true

        etTemperaturaReceta.isEnabled = true
        etTemperaturaReceta.isFocusable = true
        etTemperaturaReceta.isFocusableInTouchMode = true

        etmComentarioReceta.isEnabled = true
        etmComentarioReceta.isFocusable = true
        etmComentarioReceta.isFocusableInTouchMode = true
    }
    private fun etNoEditable(){
        etNombreReceta.isEnabled = false
        etNombreReceta.isFocusable = false
        etNombreReceta.isFocusableInTouchMode = false

        etDificultadReceta.isEnabled = false
        etDificultadReceta.isFocusable = false
        etDificultadReceta.isFocusableInTouchMode = false

        etmPreparacionReceta.isEnabled = false
        etmPreparacionReceta.isFocusable = false
        etmPreparacionReceta.isFocusableInTouchMode = false

        etTiempoReceta.isEnabled = false
        etTiempoReceta.isFocusable = false
        etTiempoReceta.isFocusableInTouchMode = false

        etTemperaturaReceta.isEnabled = false
        etTemperaturaReceta.isFocusable = false
        etTemperaturaReceta.isFocusableInTouchMode = false

        etmComentarioReceta.isEnabled = false
        etmComentarioReceta.isFocusable = false
        etmComentarioReceta.isFocusableInTouchMode = false
    }

}

