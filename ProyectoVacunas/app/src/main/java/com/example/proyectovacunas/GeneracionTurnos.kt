package com.example.proyectovacunas

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GeneracionTurnos : AppCompatActivity() {
    private lateinit var btnIr: Button
    private lateinit var btnGenerar: Button
    private lateinit var txtCentrosAcopio: Spinner
    private lateinit var txtTipoVacuna: Spinner
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

    }
    fun irMapa(){
        btnIr.setOnClickListener{
            val forma2 = Intent(this@GeneracionTurnos, MapsActivity::class.java)
            centro= txtCentrosAcopio.selectedItem.toString()
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
                addTurno()
            }
        }
    }
    fun addTurno(){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val turno = ContentValues()

        turno.put("fk_id_usuario", id_usuario)
        turno.put("tipo_vacuna", txtTipoVacuna.selectedItem.toString())
        turno.put("centro_acopio", txtCentrosAcopio.selectedItem.toString())
        agregarFirebase(email ?:"")

        if (txtCentrosAcopio.selectedItem.toString().equals("Hospital del Guasmo Sur")){
            val url="https://www.google.com.ec/maps/place/Hospital+General+Guasmo+Sur/@-2.2767636,-79.8975766,17z/data=!3m1!4b1!4m5!3m4!1s0x902d6568748e3853:0x80078f883bcaa045!8m2!3d-2.276769!4d-79.8953879?hl=es"
            turno.put("url_maps", url)
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Hospital los Ceibos")){
            val url="https://www.google.com.ec/maps/place/Hospital+del+IESS+Los+Ceibos/@-2.1779429,-79.9475906,16z/data=!4m8!1m2!2m1!1shospital+ceibos!3m4!1s0x0:0x639e483392c612ae!8m2!3d-2.1745629!4d-79.9412857?hl=es"
            turno.put("url_maps", url)
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Centro de Convenciones")){
            val url="https://www.google.com.ec/maps/place/Centro+de+Convenciones+de+Guayaquil+Sim%C3%B3n+Bol%C3%ADvar/@-2.177846,-79.978233,13z/data=!4m8!1m2!2m1!1scentro+de+convenciones!3m4!1s0x902d6da5205c1c7d:0x2e40a22fa0b1b30a!8m2!3d-2.1587384!4d-79.8873319?hl=es"
            turno.put("url_maps", url)
        }
        if (txtCentrosAcopio.selectedItem.toString().equals("Maternidad Enrique Sotomayor")){
            val url="https://www.google.com.ec/maps/place/Antigua+Maternidad+Enrique+Sotomayor/@-2.1776737,-79.978233,13z/data=!4m8!1m2!2m1!1smaternidad+enrique+sotomayor!3m4!1s0x902d6f37cfb6e11d:0x644a7b5d415cb8fa!8m2!3d-2.1976353!4d-79.8891953?hl=es"
            turno.put("url_maps", url)
        }
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
                "vacuna" to txtTipoVacuna.selectedItem.toString())
        )
        if(txtCentrosAcopio.selectedItem.toString().equals("Hospital del Guasmo Sur")){
            db.collection("users").document(email).update(
                    mapOf("url" to "https://www.google.com.ec/maps/place/Hospital+General+Guasmo+Sur/@-2.2767636,-79.8975766,17z/data=!3m1!4b1!4m5!3m4!1s0x902d6568748e3853:0x80078f883bcaa045!8m2!3d-2.276769!4d-79.8953879?hl=es")
            )
        }
        if(txtCentrosAcopio.selectedItem.toString().equals("Hospital los Ceibos")){
            db.collection("users").document(email).update(
                    mapOf("url" to "https://www.google.com.ec/maps/place/Hospital+del+IESS+Los+Ceibos/@-2.1779429,-79.9475906,16z/data=!4m8!1m2!2m1!1shospital+ceibos!3m4!1s0x0:0x639e483392c612ae!8m2!3d-2.1745629!4d-79.9412857?hl=es")
            )
        }
        if(txtCentrosAcopio.selectedItem.toString().equals("Centro de Convenciones")){
            db.collection("users").document(email).update(
                    mapOf("url" to "https://www.google.com.ec/maps/place/Centro+de+Convenciones+de+Guayaquil+Sim%C3%B3n+Bol%C3%ADvar/@-2.177846,-79.978233,13z/data=!4m8!1m2!2m1!1scentro+de+convenciones!3m4!1s0x902d6da5205c1c7d:0x2e40a22fa0b1b30a!8m2!3d-2.1587384!4d-79.8873319?hl=es")
            )
        }
        if(txtCentrosAcopio.selectedItem.toString().equals("Maternidad Enrique Sotomayor")){
            db.collection("users").document(email).update(
                    mapOf("url" to "https://www.google.com.ec/maps/place/Antigua+Maternidad+Enrique+Sotomayor/@-2.1776737,-79.978233,13z/data=!4m8!1m2!2m1!1smaternidad+enrique+sotomayor!3m4!1s0x902d6f37cfb6e11d:0x644a7b5d415cb8fa!8m2!3d-2.1976353!4d-79.8891953?hl=es")
            )
        }
    }



    fun consultarTurno(): Boolean{
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT fk_id_usuario, tipo_vacuna, centro_acopio, url_maps FROM turno WHERE fk_id_usuario='$id_usuario'", null)
        if(fila.moveToFirst()){
            return true
        }else {
            Toast.makeText(this, "Asignando turno", Toast.LENGTH_SHORT).show()
            return false
        }
        bd.close()
    }
}

