package com.example.coockingcase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coockingcase.databinding.ActivityPreparacionBinding
import objetos.Ingrediente
import objetos.Receta
import kotlinx.android.synthetic.main.activity_preparacion.*


class  ActivityPreparacion : AppCompatActivity() {

    private lateinit var binding: ActivityPreparacionBinding
    private var ingredientes: ArrayList<Ingrediente>? = arrayListOf()
    private var receta: Receta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreparacionBinding.inflate(layoutInflater)
        receta = intent.getSerializableExtra("serialzable") as Receta?
        ingredientes = intent.getSerializableExtra("ingredientes") as ArrayList<Ingrediente>?
        setContentView(binding.root)


        binding.btnSiguientepreparacion.setOnClickListener {
            pasarDatosRRI()
        }
    }

    private fun pasarDatosRRI(){
        val intent = Intent(this,ActivityTt::class.java)
        if (etPreparacion.text.toString().length>0){
            receta?.preparacion = etPreparacion.text.toString()
            val b = Bundle()
            b.putSerializable("serialzable", receta)
            intent.putExtras(b)
            intent.putExtra("ingredientes", ingredientes)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Debes rellenar el campo", Toast.LENGTH_SHORT).show()
        }
    }
}