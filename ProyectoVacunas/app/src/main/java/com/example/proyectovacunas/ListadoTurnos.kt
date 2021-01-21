package com.example.proyectovacunas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class ListadoTurnos : AppCompatActivity() {
    private lateinit var btnSalir: Button
    private lateinit var btnAtras: Button
    private lateinit var txtCedula: TextView
    private lateinit var txtVacuna: TextView
    private lateinit var txtCentroAcopio: TextView
    private lateinit var txtUrlMaps: TextView
    private lateinit var txtNombres: TextView
    private lateinit var txtApellidos: TextView
    private lateinit var txtFechaTurno: TextView
    private lateinit var txtHoraTurno: TextView

    var fk_usuario: String=""
    fun init(){
        btnSalir = findViewById(R.id.btn_Salir)
        btnAtras = findViewById(R.id.btn_Atras)
        txtCedula = findViewById(R.id.txt_Ci)
        txtVacuna = findViewById(R.id.txt_vacuna)
        txtCentroAcopio = findViewById(R.id.txt_Centro)
        txtUrlMaps = findViewById(R.id.txt_urlMaps)
        txtNombres = findViewById(R.id.txt_nombres)
        txtApellidos = findViewById(R.id.txt_apellidos)
        txtFechaTurno = findViewById(R.id.txtFechaTurno)
        txtHoraTurno = findViewById(R.id.txtHoraTurno)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_turnos)
        init()
        val objetoIntent : Intent = intent
        fk_usuario= objetoIntent.getStringExtra("fk_usuario").toString()
        datosUsuario()
        buscarTurno()
        click_atras()

       /* btnSalir.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val forma2 = Intent(this@ListadoTurnos, MainActivity::class.java)
            startActivity(forma2)
        }*/
    }




    private fun turnoAlerta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Turno")
        builder.setMessage("Usted no tiene un turno asignado.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun click_atras(){
        btnAtras.setOnClickListener{
            val forma2 = Intent(this@ListadoTurnos, MenuOpciones::class.java)
            forma2.putExtra("id_usuario", fk_usuario)
            startActivity(forma2)
        }
    }
    fun buscarTurno(){
        //Conexion a la base de datos
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT tipo_vacuna, centro_acopio, url_maps, fecha, hora FROM turno WHERE fk_id_usuario='${fk_usuario}'", null)
        if(fila.moveToFirst()){
            txtVacuna.setText(fila.getString(0))
            txtCentroAcopio.setText(fila.getString(1))
            txtUrlMaps.setText(fila.getString(2))
            val content = SpannableString(txtUrlMaps.text.toString())
            content.setSpan(UnderlineSpan(), 0, txtUrlMaps.length(), 0)
            txtUrlMaps.setText(content)
            txtUrlMaps.setOnClickListener(){
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(txtUrlMaps.text.toString())
                startActivity(i)
            }
            txtFechaTurno.setText(fila.getString(3))
            txtHoraTurno.setText(fila.getString(4))

        }else {
            Toast.makeText(this, "Usuario NO Tiene turno", Toast.LENGTH_SHORT).show()
            this.finish()
        }
        bd.close()
    }
    fun datosUsuario(){
        val admin = UserSqliteOpenHelper(this, "Bd_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT cedula, nombre, apellido FROM usuario WHERE id_usuario='${fk_usuario}'", null)
        if(fila.moveToFirst()){
            txtCedula.setText(fila.getString(0))
            txtNombres.setText(fila.getString(1))
            txtApellidos.setText(fila.getString(2))
        }else {
            Toast.makeText(this, "Error en obtener datos usuario", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }


}