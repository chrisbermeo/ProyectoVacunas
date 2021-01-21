package com.example.proyectovacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class MenuOpciones : AppCompatActivity() {
    private lateinit var btnGeneracion: CardView
    private lateinit var btnInformacion: CardView
    private lateinit var btnTurno: CardView
    private lateinit var btnSalir: Button
    var id_usuario: String=""
    var email: String=""
    fun init(){
        btnGeneracion = findViewById(R.id.btnGeneracion)
        btnInformacion = findViewById(R.id.btnInformacion)
        btnTurno = findViewById(R.id.btnTurno)
        btnSalir = findViewById(R.id.btnSalir)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_opciones)
        val objetoIntent : Intent = intent
        id_usuario= objetoIntent.getStringExtra("id_usuario").toString()
        email= objetoIntent.getStringExtra("email").toString()
        init()
        click_Generar()
        click_Informacion()
        click_Turno()
        click_CerrarSesion()
    }
    fun click_Generar(){
        btnGeneracion.setOnClickListener{
            val forma2 = Intent(this@MenuOpciones, GeneracionTurnos::class.java)
            forma2.putExtra("id_usuario", id_usuario)
            forma2.putExtra("email", email)
            Toast.makeText(this, "$id_usuario $email",  Toast.LENGTH_SHORT).show()
            startActivity(forma2)
        }
    }
    fun click_Informacion(){
        btnInformacion.setOnClickListener{
            val forma2 = Intent(this@MenuOpciones, InformacionVacunas::class.java)
            startActivity(forma2)
        }
    }
    fun click_Turno(){
        btnTurno.setOnClickListener{
            val forma2 = Intent(this@MenuOpciones, ListadoTurnos::class.java)
            forma2.putExtra("fk_usuario", id_usuario)
            forma2.putExtra("email", email)
            startActivity(forma2)
        }
    }
    fun click_CerrarSesion(){
        btnSalir.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val forma2 = Intent(this@MenuOpciones, MainActivity::class.java)
            startActivity(forma2)
        }

    }
}