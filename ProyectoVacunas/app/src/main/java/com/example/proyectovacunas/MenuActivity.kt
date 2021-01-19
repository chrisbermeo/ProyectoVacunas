package com.example.proyectovacunas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType{
    BASIC
}

class MenuActivity : AppCompatActivity() {
    private lateinit var tvCorreo : TextView
    private lateinit var tvProveedor : TextView
    private lateinit var btnCerrarSesion : Button
    private lateinit var txtVacuna : EditText
    private lateinit var txtCentro : EditText
    private lateinit var btnAdd : Button
    private val db = FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvProveedor = findViewById(R.id.tvProveedor)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        txtCentro = findViewById(R.id.txtCentro)
        txtVacuna = findViewById(R.id.txtVacuna)
        btnAdd = findViewById(R.id.btnAdd)

        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")

        setup(email ?:"", provider ?:"")
        add(email ?:"")


    }

    private fun setup(email: String, provider: String){
        title = "Inicio"
        tvCorreo.text = email
        tvProveedor.text = provider

        btnCerrarSesion.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

    }

    private fun add(email: String){
        btnAdd.setOnClickListener{
           db.collection("users").document(email).update(
               mapOf("centro_medico" to txtCentro.text.toString(),
               "vacuna" to txtVacuna.text.toString())
           )
        }
    }
}