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
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtCorreo.text.toString(),
                        txtPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
//<<<<<<< HEAD
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
//=======
                        consultarUsuarios()
//>>>>>>> 6d96fc234037f77acf02e5e4562f3d49e98bc96f
                    } else {
                        showAlert()
                    }
                }
            }else{
                showAlert()
            }
        }

        btnRegistrarse.setOnClickListener(){
            registro()
        }

    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de autenticacion del usuario")
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


    fun registro() {
        val irRegistro = Intent(this@MainActivity, Registrarse::class.java)
        startActivity(irRegistro)
    }

    fun consultarUsuarios(){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT id_usuario, correo, password FROM usuario", null)
        while(fila.moveToNext()){
            if ((fila.getString(1).equals(txtCorreo.getText().toString())) && (fila.getString(2).equals(txtPassword.getText().toString()) )){
                val forma2 = Intent(this@MainActivity, MenuOpciones::class.java)
                val Id_usuario = fila.getString(0)
                forma2.putExtra("id_usuario", Id_usuario)
                Toast.makeText(this, "$Id_usuario",  Toast.LENGTH_SHORT).show()
                startActivity(forma2)
                break
            }
        }
        bd.close()
    }
}
