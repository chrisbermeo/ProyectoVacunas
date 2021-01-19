package com.example.proyectovacunas

import android.content.Intent
//<<<<<<< HEAD
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var btnIngresar: Button
    private lateinit var btnRegistrarse: Button
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        setup()
    }

    private fun setup() {
        btnIngresar.setOnClickListener() {

            if (txtCorreo.text.isNotEmpty() && txtPassword.text.isNotEmpty()) {
                //consultarUsuarios()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtCorreo.text.toString(),
                        txtPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        irTurnos()
                    } else {
                        showAlert()
                    }
                }
            }else{
                showAlert()
            }
        }

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido unu error de autenticacion del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this@MainActivity, MenuActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


    fun registro(v: View) {
        val irRegistro = Intent(this@MainActivity, Registrarse::class.java)
        startActivity(irRegistro)
    }

    fun irTurnos() {
        val forma2 = Intent(this@MainActivity, GeneracionTurnos::class.java)
        startActivity(forma2)
    }
    fun consultarUsuarios(){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT id_usuario, correo, password FROM usuario", null)
        while(fila.moveToNext()){
            if ((fila.getString(1).equals(txtCorreo.getText().toString())) && (fila.getString(2).equals(txtPassword.getText().toString()) )){
                //definir bien a donde hay que dirigirnos en este caso está al mapa
                val forma2 = Intent(this@MainActivity, MapsActivity::class.java)
                forma2.putExtra("id_usuario", fila.getString(0))
                startActivity(forma2)
                break
            }
        }
        bd.close()
    }

}

//=======

/*
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
>>>>>>> 0b35b2b4493afe52737d47acaaebae9adc747477
    }*/
