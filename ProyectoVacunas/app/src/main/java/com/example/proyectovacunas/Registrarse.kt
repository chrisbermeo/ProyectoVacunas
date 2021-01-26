package com.example.proyectovacunas


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Registrarse : AppCompatActivity() {

    private lateinit var btnRegistrarse: Button
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtApellido: EditText
    private lateinit var txtCedula: EditText
    private lateinit var tvLogin:TextView

    private val db = FirebaseFirestore.getInstance()

    fun init() {
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        txtFecha = findViewById(R.id.txtFechaNacimiento)
        txtNombre = findViewById(R.id.txtNombres)
        txtApellido = findViewById(R.id.txtApellidos)
        txtCedula = findViewById(R.id.txtCedula)
        tvLogin = findViewById(R.id.tvLogin)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        init()
        setup()
        txtFecha.setOnClickListener { showDatePickerDialog() }
        tvLogin.setOnClickListener{irLogin()}
    }

    private fun irLogin() {
        val forma2 = Intent(this,MainActivity::class.java)
        startActivity(forma2)
        finish()
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        txtFecha.setText("$day-$month-$year")
    }

    private fun guardarFirebase() {
        db.collection("users").document(txtCorreo.text.toString())
                .set( //Se crea una colección de datos para guardar los datos de todos los usuarios
                        hashMapOf(
                                "cedula" to txtCedula.text.toString(),
                                "nombres" to txtNombre.text.toString(),
                                "apellidos" to txtApellido.text.toString(),
                                "fecha_nacimiento" to txtFecha.text.toString(),
                                "vacuna" to "",
                                "centro_medico" to "",
                                "url" to "",
                                "fecha_turno" to "",
                                "hora_turno" to ""
                        )
                )
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No se ha podido registrar al usuario, por favor intentelo de nuevo.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertCedula() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Advertencia")
        builder.setMessage("La cédula está incorrecta\nDebe tener 10 dígitos.")
        builder.setPositiveButton("Intentar de nuevo.", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun alertCampos() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Advertencia")
        builder.setMessage("Debe llenar todos los campos.")
        builder.setPositiveButton("Intentar de nuevo.", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    fun addUsuario() {
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val user = ContentValues()
        user.put("cedula", txtCedula.getText().toString())
        user.put("nombre", txtNombre.getText().toString())
        user.put("apellido", txtApellido.getText().toString())
        user.put("fechaNacimiento", txtFecha.getText().toString())
        user.put("correo", txtCorreo.getText().toString())
        user.put("password", txtPassword.getText().toString())

        if (bd.insert("usuario", null, user) > -1) {
            Toast.makeText(this, "Usuario agregado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Usuario NO agregado", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }

    private fun setup() {
        title = "Autenticación"
        btnRegistrarse.setOnClickListener() {
            if (txtCedula.text.isEmpty() || txtNombre.text.isEmpty() || txtApellido.text.isEmpty() || txtFecha.text.isEmpty() || txtCorreo.text.isEmpty() || txtPassword.text.isEmpty()) {
                alertCampos()
            } else if (txtCedula.text.toString().length >= 11 || txtCedula.text.toString().length <= 9) {
                alertCedula()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        txtCorreo.text.toString(),
                        txtPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        addUsuario()
                        guardarFirebase()
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        finish()
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

}
