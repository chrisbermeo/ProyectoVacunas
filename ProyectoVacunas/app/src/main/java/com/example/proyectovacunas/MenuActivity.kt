package com.example.proyectovacunas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class MenuActivity : AppCompatActivity() {
    private lateinit var tvCorreo : TextView
    private lateinit var tvProveedor : TextView
    private lateinit var btnCerrarSesion : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvProveedor = findViewById(R.id.tvProveedor)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)


        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        setup(email ?:"", provider ?:"")


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
}