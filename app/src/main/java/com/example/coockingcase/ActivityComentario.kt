package com.example.coockingcase

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.coockingcase.databinding.ActivityComentarioBinding
import objetos.Comentario
import kotlinx.android.synthetic.main.activity_comentario.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ActivityComentario : AppCompatActivity() {

    private lateinit var binding: ActivityComentarioBinding
    private var lastid: Int = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityComentarioBinding.inflate(layoutInflater)
        lastid = intent.getIntExtra("lastid", 0)
        setContentView(binding.root)

        binding.etFecha.setOnClickListener {
            currentDate()
        }

        binding.btnFinalizarcomentario.setOnClickListener {
            guardarComentario()

        }
    }

    private fun guardarComentario(){
        val intent = Intent(this, MainActivity::class.java)
        var dbHelper = DBHelper(applicationContext)
        if (etComentario.text.toString().length>0 && etFecha.text.toString().length>0){
            var comentario = Comentario(etComentario.text.toString(),etFecha.text.toString())
            dbHelper.insertComentario(lastid,comentario)
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Debes rellenar los campos", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun currentDate(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formated = current.format(formatter)

        etFecha.setText(formated)
    }


}