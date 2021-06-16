package com.example.coockingcase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coockingcase.databinding.ActivityTtBinding
import objetos.Ingrediente
import objetos.Receta
import kotlinx.android.synthetic.main.activity_tt.*

class ActivityTt : AppCompatActivity() {

    private lateinit var binding: ActivityTtBinding
    private var ingredientes: ArrayList<Ingrediente> = arrayListOf()
    private var receta: Receta? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTtBinding.inflate(layoutInflater)
        receta = intent.getSerializableExtra("serialzable") as Receta?
        ingredientes = (intent.getSerializableExtra("ingredientes") as ArrayList<Ingrediente>?)!!
        setContentView(binding.root)

        binding.btnSiguientett.setOnClickListener {
            insertarDatosPassId()
        }
    }
    private fun insertarDatosPassId(){
        var dbHelper = DBHelper(applicationContext)
        val intent = Intent(this,ActivityFoto::class.java)
        if (etTiempo.text.toString().length>0 && etTemperatura.text.toString().length>0){
            receta?.tiempo = etTiempo.text.toString()
            receta?.temperatura = etTemperatura.text.toString()
            // Insertamos la receta
            var lastid = dbHelper.insertReceta(receta)
            receta?.id = lastid
            // Recorremos los ingredientes y los insertamos
            ingredientes?.forEach {
                it.id_receta = lastid
                dbHelper.insertIngrediente(it)
            }
            val b = Bundle()
            b.putInt("lastid", lastid)
            intent.putExtras(b)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Debes rellenar los campos", Toast.LENGTH_SHORT).show()
        }
    }
}