package com.example.proyectovacunas


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Provider
import java.util.*

class Registrarse : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var btnRegistrarse: Button
    private lateinit var txtCorreo : EditText
    private lateinit var txtPassword : EditText
    private lateinit var txtFecha : EditText
    private lateinit var txtNombre : EditText
    private lateinit var txtApellido : EditText
    private lateinit var txtCedula : EditText

    private lateinit var txtNombres: TextView
    private lateinit var txtApellidos: TextView
    private lateinit var txtFechaNacimiento: TextView

    var dia = 0
    var mes = 0
    var anio = 0
    var hora = 0
    var minuto = 0

    var savedDia = 0
    var savedMes = 0
    var savedAnio = 0
    var savedHora = 0
    var savedMinuto = 0

    fun init(){
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtPassword)
        txtFecha = findViewById(R.id.txtFechaNacimiento)
        txtNombre = findViewById(R.id.txtNombres)
        txtApellido = findViewById(R.id.txtApellidos)
        txtCedula = findViewById(R.id.txtCedula)
    }

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        init()
        setup()
        pickDate()
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance(Locale.US)
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        anio = cal.get(Calendar.YEAR)
        hora = cal.get(Calendar.HOUR)
        minuto = cal.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        txtFecha.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this, this, dia,mes,anio).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDia = dayOfMonth
        savedMes = month
        savedAnio = year

        getDateTimeCalendar()
        txtFecha.setText("$savedDia-$savedMes-$savedAnio").toString()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }



    /*fun fecha(v:View){
        showDatePickerDialog()
    }


    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            txtFecha.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }*/



    private fun setup(){
        title ="Autenticación"
        btnRegistrarse.setOnClickListener(){
            if (txtCorreo.text.isNotEmpty() && txtPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(txtCorreo.text.toString(),
                txtPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
//<<<<<<< HEAD
                        //addUsuario()  agregar en la base de datos local
                        guardarFirebase()
//=======
                        addUsuario()
//>>>>>>> 6d96fc234037f77acf02e5e4562f3d49e98bc96f
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)

                    }else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun guardarFirebase(){
        db.collection("users").document(txtCorreo.text.toString()).set(
            hashMapOf("cedula" to txtCedula.text.toString(),
            "nombres" to txtNombre.text.toString(),
            "apellidos" to txtApellido.text.toString(),
            "fecha_nacimiento" to txtFecha.text.toString(),
            "vacuna" to "",
            "centro_medico" to "")
        )   //Se crea una colección de datos para guardar los datos de todos los usuarios
    }


    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No se ha podido registrar al usuario, por favor intentenlo de nuevo.")
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
        user.put("nombre", txtNombre.getText().toString())
        user.put("apellido", txtApellido.getText().toString())
        user.put("fechaNacimiento", txtFecha.getText().toString())
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