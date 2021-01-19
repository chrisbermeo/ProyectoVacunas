package com.example.proyectovacunas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserSqliteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory,version){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL( "CREATE TABLE usuario(id_usuario integer primary key autoincrement, cedula text, nombre text, apellido text, fechaNacimiento text, correo text, password text)")
        //db.execSQL( "CREATE TABLE turno(id_turno integer primary key autoincrement, fk_idUsuario integer foreign key, tipo_vacuna text, centro_acopio text, url_maps text )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}