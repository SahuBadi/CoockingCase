package com.example.coockingcase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coockingcase.databinding.ActivityTransicionBinding

class ActivityTransicion : AppCompatActivity() {

    private lateinit var binding: ActivityTransicionBinding
    private var lastid: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTransicionBinding.inflate(layoutInflater)
        lastid = intent.getIntExtra("lastid", 0)
        setContentView(binding.root)

        binding.btnFinalizar.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSi.setOnClickListener {
            pasarId()
        }
    }
    private fun pasarId(){
        var intent = Intent(this, ActivityComentario::class.java)
        val b = Bundle()
        b.putInt("lastid", lastid)
        intent.putExtras(b)
        startActivity(intent)
    }
}