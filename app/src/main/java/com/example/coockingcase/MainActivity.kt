package com.example.coockingcase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coockingcase.R.style.Theme_CoockingCase
import com.example.coockingcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Theme_CoockingCase)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.btnVer.setOnClickListener {
            val intent = Intent(this,ActivityRecetas::class.java)
            startActivity(intent)
        }
        binding.btnCrear.setOnClickListener {
            val intent = Intent(this,ActivityNombre::class.java)
            startActivity(intent)
        }

    }
}