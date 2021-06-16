package com.example.coockingcase

import Adapters.RecetasAdapter
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coockingcase.databinding.ActivityRecetasBinding
import kotlinx.android.synthetic.main.activity_recetas.*
import java.util.*

class ActivityRecetas : AppCompatActivity() {
    private lateinit var binding: ActivityRecetasBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var db: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRecetasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        db = dbHelper.readableDatabase
        var cursor = db.rawQuery("select * from recetas, fotos where fotos.Id_Receta = recetas.Id and fotos.id = (select min(fotos.Id) from fotos where fotos.Id_Receta = recetas.id) order by Nombre", null)

        val adaptador = RecetasAdapter()
        adaptador.RecetasAdapter(this, cursor)

        adaptador.notifyDataSetChanged()

        binding.rvReceta.setHasFixedSize(true)
        binding.rvReceta.layoutManager = LinearLayoutManager(this)
        binding.rvReceta.adapter = adaptador

        binding.btnSalirrecetas.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.sevBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                sevBuscar.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    val search = newText.toUpperCase(Locale.getDefault())
                    val params = arrayOfNulls<String>(1)
                    params[0] = "%" + search + "%"
                    adaptador.cursor = db.rawQuery("select * from recetas, fotos where fotos.Id_Receta = recetas.Id and fotos.id = (select min(fotos.Id) from fotos where fotos.Id_Receta = recetas.id) and Nombre like ? order by Nombre", params)
                } else
                    adaptador.cursor = db.rawQuery("select * from recetas, fotos where fotos.Id_Receta = recetas.Id and fotos.id = (select min(fotos.Id) from fotos where fotos.Id_Receta = recetas.id) order by Nombre", null)
                binding.rvReceta.layoutManager = LinearLayoutManager(applicationContext)
                binding.rvReceta.adapter = adaptador
                return true
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}


