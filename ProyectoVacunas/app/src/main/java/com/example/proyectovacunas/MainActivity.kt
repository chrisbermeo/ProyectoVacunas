package com.example.proyectovacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnIngresar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //probando la interfaz de mapa
        btnIngresar = findViewById(R.id.btnIngresar)
        btnIngresar.setOnClickListener{
            val forma2 = Intent(this@MainActivity, GeneracionTurnos::class.java)
            startActivity(forma2)
        }
    }
}