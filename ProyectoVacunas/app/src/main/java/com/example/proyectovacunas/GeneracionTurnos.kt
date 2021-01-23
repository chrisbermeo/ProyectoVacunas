package com.example.proyectovacunas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GeneracionTurnos : AppCompatActivity() {
    private lateinit var btnIr: Button
    private lateinit var btnGenerar: Button
    private lateinit var txtCentrosAcopio: Spinner
    private lateinit var txtTipoVacuna: Spinner
    private lateinit var txt_Fecha: EditText
    private lateinit var txt_Hora: EditText
    var centro: String =""
    var id_usuario: String=""
    var email: String=""
    private val db = FirebaseFirestore.getInstance()

    //val bundle:Bundle? = intent.extras
    //val email:String? = bundle?.getString("email")


    fun init(){
        btnIr = findViewById(R.id.btnIrMaps)
        txtCentrosAcopio = findViewById(R.id.txtCentrosAcopio)
        txtTipoVacuna = findViewById(R.id.txtVacunas)
        btnGenerar = findViewById(R.id.btnGenerar)
        txt_Fecha = findViewById(R.id.txt_Fecha)
        txt_Hora = findViewById(R.id.txt_hora)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generacion_turnos)
        init()
        val objetoIntent : Intent = intent
        id_usuario= objetoIntent.getStringExtra("id_usuario").toString()
        email= objetoIntent.getStringExtra("email").toString()
        irMapa()
        btnGenerarTurno()
        txt_Fecha.setOnClickListener { showDatePickerDialog() }
        txt_Hora.setOnClickListener { showTimePickerDialog() }


    }

    private fun showTimePickerDialog() {
        val timePicker=TimePickerFragment{onTimeSelected(it)}
        timePicker.show(supportFragmentManager,"time")
    }

    private fun onTimeSelected(time:String){
        txt_Hora.setText("$time")
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerTurno { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManager, "fechaTurnos")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        txt_Fecha.setText("$day-$month-$year")
    }

    fun irMapa(){
        btnIr.setOnClickListener{
            centro = txtCentrosAcopio.selectedItem.toString()
            val forma2 = Intent(this@GeneracionTurnos, MapsActivity::class.java)
            Toast.makeText(this, "$centro", Toast.LENGTH_SHORT).show()
            forma2.putExtra("centro_acopio", centro)
            startActivity(forma2)
        }
    }
    fun btnGenerarTurno(){
        btnGenerar.setOnClickListener{
            if (consultarTurno()){
                Toast.makeText(this, "Ya tiene un turno asignado", Toast.LENGTH_SHORT).show()
                val forma2 = Intent(this@GeneracionTurnos, ListadoTurnos::class.java)
                forma2.putExtra("fk_usuario", id_usuario)
                startActivity(forma2)
            }else {
                if (txt_Fecha.text.isEmpty() || txt_Hora.text.isEmpty()){
                    Toast.makeText(this, "Advertencia: Campos vacios", Toast.LENGTH_SHORT).show()
                }else{
                    if (consultarFechaHora()){
                        Toast.makeText(this, "Horario y centro de acopio no disponibles", Toast.LENGTH_SHORT).show()
                    }else{
                        addTurno()
                    }
                }
            }
        }
    }
    fun addTurno(){
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val turno = ContentValues()

        turno.put("fk_id_usuario", id_usuario)
        turno.put("tipo_vacuna", txtTipoVacuna.selectedItem.toString())
        turno.put("centro_acopio", txtCentrosAcopio.selectedItem.toString())
        agregarFirebase(email ?:"")

        if (txtCentrosAcopio.selectedItem.toString().equals("Hospital del Guasmo Sur")){
            val url="https://www.google.com.ec/maps/place/Hospital+General+Guasmo+Sur/@-2.2767636,-79.8975766,17z/data=!3m1!4b1!4m5!3m4!1s0x902d6568748e3853:0x80078f883bcaa045!8m2!3d-2.276769!4d-79.8953879?hl=es"
            turno.put("url_maps", url)
            db.collection("users").document(email).update(
                    mapOf("url" to url)
            )
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Hospital los Ceibos")){
            val url="https://www.google.com.ec/maps/place/Hospital+del+IESS+Los+Ceibos/@-2.1779429,-79.9475906,16z/data=!4m8!1m2!2m1!1shospital+ceibos!3m4!1s0x0:0x639e483392c612ae!8m2!3d-2.1745629!4d-79.9412857?hl=es"
            turno.put("url_maps", url)
            db.collection("users").document(email).update(
                    mapOf("url" to url)
            )
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Centro de Convenciones")){
            val url="https://www.google.com.ec/maps/place/Centro+de+Convenciones+de+Guayaquil+Sim%C3%B3n+Bol%C3%ADvar/@-2.177846,-79.978233,13z/data=!4m8!1m2!2m1!1scentro+de+convenciones!3m4!1s0x902d6da5205c1c7d:0x2e40a22fa0b1b30a!8m2!3d-2.1587384!4d-79.8873319?hl=es"
            turno.put("url_maps", url)
            db.collection("users").document(email).update(
                    mapOf("url" to url)
            )
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Maternidad Enrique Sotomayor")){
            val url="https://www.google.com.ec/maps/place/Antigua+Maternidad+Enrique+Sotomayor/@-2.1776737,-79.978233,13z/data=!4m8!1m2!2m1!1smaternidad+enrique+sotomayor!3m4!1s0x902d6f37cfb6e11d:0x644a7b5d415cb8fa!8m2!3d-2.1976353!4d-79.8891953?hl=es"
            turno.put("url_maps", url)
            db.collection("users").document(email).update(
                    mapOf("url" to url)
            )
        }
        turno.put("fecha", txt_Fecha.text.toString())
        turno.put("hora", txt_Hora.text.toString())
        if(bd.insert("turno", null, turno)>-1){
            Toast.makeText(this, "Turno asignado", Toast.LENGTH_SHORT).show()
            val forma2 = Intent(this@GeneracionTurnos, ListadoTurnos::class.java)
            val fk_usuario= id_usuario
            forma2.putExtra("fk_usuario", fk_usuario)
            startActivity(forma2)
        }else{
            Toast.makeText(this, "Turno NO agregado", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }

    private fun agregarFirebase(email: String){
        db.collection("users").document(email).update(
                mapOf("centro_medico" to txtCentrosAcopio.selectedItem.toString(),
                        "vacuna" to txtTipoVacuna.selectedItem.toString(),
                        "fecha_turno" to txt_Fecha.text.toString(),
                        "hora_turno" to txt_Hora.text.toString()))
    }



    fun consultarTurno(): Boolean{
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT fk_id_usuario, tipo_vacuna, centro_acopio, url_maps FROM turno WHERE fk_id_usuario='$id_usuario'", null)
        if(fila.moveToFirst()){
            return true
        }else {
            Toast.makeText(this, "Consultando...", Toast.LENGTH_SHORT).show()
            return false
        }
        bd.close()
    }
    fun consultarFechaHora(): Boolean{
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fecha= txt_Fecha.text.toString()
        val hora= txt_Hora.text.toString()
        //Toast.makeText(this, "$fecha, $hora, $centro", Toast.LENGTH_SHORT).show()
        val fila = bd.rawQuery("SELECT fk_id_usuario FROM turno WHERE fecha='$fecha' AND hora='$hora' AND centro_acopio='$centro'", null)
        if(fila.moveToFirst()){
            Toast.makeText(this, "Verificando....", Toast.LENGTH_SHORT).show()
            return true
        }else {
            Toast.makeText(this, "Asignando turno....", Toast.LENGTH_SHORT).show()
            return false
        }
        bd.close()
    }

}


