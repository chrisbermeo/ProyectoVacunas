package com.example.proyectovacunas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ListadoTurnos : AppCompatActivity() {
    private lateinit var btnSalir: Button
    private lateinit var btnAtras: Button
    private lateinit var txtCedula: TextView
    private lateinit var txtVacuna: TextView
    private lateinit var txtCentroAcopio: TextView
    private lateinit var txtUrlMaps: TextView
    private lateinit var txtNombres: TextView
    private lateinit var txtApellidos: TextView
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
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_turnos)
        init()
        val objetoIntent : Intent = intent
        fk_usuario= objetoIntent.getStringExtra("fk_usuario").toString()
        datosUsuario()
        buscarTurno()
        btnAtras.setOnClickListener{
            //debe ir al menos
        }
        btnSalir.setOnClickListener{
            //debe cerrar sesion
        }
    }
    fun buscarTurno(){
        //Conexion a la base de datos
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT tipo_vacuna, centro_acopio, url_maps FROM turno WHERE fk_id_usuario='${fk_usuario}'", null)
        if(fila.moveToFirst()){
            txtVacuna.setText(fila.getString(0))
            txtCentroAcopio.setText(fila.getString(1))
            txtUrlMaps.setText(fila.getString(2))
        }else {
            Toast.makeText(this, "Usuario NO EXISTE", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }
    fun datosUsuario(){
        val admin = UserSqliteOpenHelper(this, "BD_usuarios", null, 1)
        val bd = admin.writableDatabase
        val fila = bd.rawQuery("SELECT cedula, nombre, apellido FROM usuario WHERE id_usuario='${fk_usuario}'", null)
        if(fila.moveToFirst()){
            txtCedula.setText(fila.getString(0))
            txtNombres.setText(fila.getString(1))
            txtApellidos.setText(fila.getString(2))
        }else {
            Toast.makeText(this, "Usuario NO EXISTE", Toast.LENGTH_SHORT).show()
        }
        bd.close()
    }

}