package com.example.coockingcase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coockingcase.databinding.ActivityNombreBinding
import objetos.Receta
import kotlinx.android.synthetic.main.activity_nombre.*

class ActivityNombre : AppCompatActivity() {

    private lateinit var receta : Receta

    private lateinit var binding: ActivityNombreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNombreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalirnombre.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSiguientenombre.setOnClickListener {
            pasarDatos()
        }
    }
    private fun pasarDatos(){
        if (etNombre.text.toString().length>0 && etDificultad.text.toString().length>0){
            receta = Receta(etNombre.text.toString(), etDificultad.text.toString())
            val intent = Intent(this,ActivityIngredientes::class.java)
            val b = Bundle()
            b.putSerializable("serialzable", receta)
            intent.putExtras(b)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Debes rellenar los campos",Toast.LENGTH_SHORT).show()
        }
    }
}