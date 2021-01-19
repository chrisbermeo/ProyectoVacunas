package com.example.proyectovacunas

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import java.security.Provider

class Registrarse : AppCompatActivity() {
    private lateinit var btnRegistrarse: Button
    private lateinit var txtCorreo : EditText
    private lateinit var txtPassword : EditText
    private lateinit var txtCedula: TextView
    private lateinit var txtNombres: TextView
    private lateinit var txtApellidos: TextView
    private lateinit var txtFechaNacimiento: TextView
    fun init(){
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        txtCedula = findViewById(R.id.txtCedula)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtNombres = findViewById(R.id.txtNombres)
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        init()

        setup()
    }

    private fun setup(){
        title ="Autenticación"
        btnRegistrarse.setOnClickListener(){
            if (txtCorreo.text.isNotEmpty() && txtPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtCorreo.text.toString(),
                txtPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        addUsuario()
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider : ProviderType){
        val homeIntent = Intent(this,MenuActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    fun addUsuario(){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val user = ContentValues()
        user.put("cedula", txtCedula.getText().toString())
        user.put("nombre", txtNombres.getText().toString())
        user.put("apellido", txtApellidos.getText().toString())
        user.put("fechaNacimiento", txtFechaNacimiento.getText().toString())
        user.put("correo", txtCorreo.getText().toString())
        user.put("password", txtPassword.getText().toString())

        if(bd.insert("usuario", null, user)>-1){
            Toast.makeText(this, "Usuario agregado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Usuario NO agregado", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }

}