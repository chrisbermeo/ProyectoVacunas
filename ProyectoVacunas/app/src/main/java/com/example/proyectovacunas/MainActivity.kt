package com.example.proyectovacunas

import android.content.Intent
//<<<<<<< HEAD
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var btnIngresar: Button
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText
    private lateinit var tvRegistrate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnIngresar = findViewById(R.id.btnIngresar)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        tvRegistrate = findViewById(R.id.tvRegistrate)
        setup()
    }

    private fun setup() {
        btnIngresar.setOnClickListener() {

            if (txtCorreo.text.isNotEmpty() && txtPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(txtCorreo.text.toString(),
                        txtPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful) {
                        consultarUsuarios(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }else{
                alertaCampos()
            }
        }
        tvRegistrate.setOnClickListener(){
            registro()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Contraseña incorrecta")
        builder.setMessage("La contraseña o usuario que ingresaste no es correcta.\n Vuelve a intentarlo")
        builder.setPositiveButton("Intentar de nuevo", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertaCampos() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Campos incompletos")
        builder.setMessage("No ha ingresado el correo o contraseña.\n Vuelva a intentarlo")
        builder.setPositiveButton("Intentar de nuevo", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun registro() {
        val irRegistro = Intent(this@MainActivity, Registrarse::class.java)
        startActivity(irRegistro)
    }

    fun consultarUsuarios(email: String, provider: ProviderType){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT id_usuario, correo, password FROM usuario", null)
        while(fila.moveToNext()){
            if ((fila.getString(1).equals(txtCorreo.getText().toString())) && (fila.getString(2).equals(txtPassword.getText().toString()) )){
                val forma2 = Intent(this@MainActivity, MenuOpciones::class.java)
                val Id_usuario = fila.getString(0)
                forma2.putExtra("id_usuario", Id_usuario)
                forma2.putExtra("email", email)
                forma2.putExtra("provider", provider.name)
                Toast.makeText(this, "$Id_usuario",  Toast.LENGTH_SHORT).show()
                startActivity(forma2)
                break
            }
        }
        bd.close()
    }
}
