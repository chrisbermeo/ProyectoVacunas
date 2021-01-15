package com.example.proyectovacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap

class GeneracionTurnos : AppCompatActivity() {
    lateinit var btnIr: Button
    private lateinit var mMap: GoogleMap
    lateinit var txtCentrosAcopio: Spinner
    lateinit var txtSector: Spinner
    lateinit var txtTipoVacuna: Spinner
    fun init(){
        btnIr = findViewById(R.id.btnIrMaps)
        txtCentrosAcopio = findViewById(R.id.txtCentrosAcopio)
        txtSector = findViewById(R.id.txtSector)
        txtTipoVacuna = findViewById(R.id.txtVacunas)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generacion_turnos)
        init()
        btnIr.setOnClickListener{
            val forma2 = Intent(this@GeneracionTurnos, MapsActivity::class.java)
            startActivity(forma2)
        }
    }
}