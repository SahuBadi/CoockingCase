package com.example.coockingcase

import Adapters.IngredientesAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coockingcase.databinding.ActivityIngredientesBinding
import objetos.Ingrediente
import objetos.Receta
import kotlinx.android.synthetic.main.activity_ingredientes.*

class ActivityIngredientes : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientesBinding
    private var ingredientes: ArrayList<Ingrediente> = arrayListOf()
    private var receta: Receta? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIngredientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        receta = intent.getSerializableExtra("serialzable") as Receta?

        binding.btnGuardaringredientes.setOnClickListener {
            preservarIngredites()
        }

        binding.btnSiguienteingredientes.setOnClickListener {
            if (ingredientes.count()>0){
                pasarDatosRI()
            }else {
                Toast.makeText(this, "Debes introducir al menos un ingrediente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertaIngrediente (nombre: String, cantidad: String) {
        var ingrediente = Ingrediente(nombre, cantidad)
        ingredientes.add(ingrediente)
    }

    private fun refrescarVistaIngredientes () {
        val adaptador = IngredientesAdapter(ingredientes)
        binding.rvIngredientes.setHasFixedSize(true)
        binding.rvIngredientes.layoutManager = LinearLayoutManager(this)
        binding.rvIngredientes.adapter = adaptador
    }
    private fun preservarIngredites(){
        if (etIngrediente.text.toString().length>0 && etCantidad.text.toString().length>0){
            insertaIngrediente(etIngrediente.text.toString(), etCantidad.text.toString())
            refrescarVistaIngredientes()
            etIngrediente.setText("")
            etCantidad.setText("")
            etIngrediente.requestFocus()
        }else{
            Toast.makeText(this, "Debes rellenar los campos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun pasarDatosRI(){
        val intent = Intent(this, ActivityPreparacion::class.java)
        val b = Bundle()
        b.putSerializable("serialzable", receta)
        intent.putExtras(b)
        intent.putExtra("ingredientes", ingredientes)
        startActivity(intent)
    }
}