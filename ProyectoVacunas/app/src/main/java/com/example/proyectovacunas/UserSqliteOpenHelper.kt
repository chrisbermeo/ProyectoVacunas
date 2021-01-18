package com.example.proyectovacunas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserSqliteOpenHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory,version){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL( "CREATE TABLE usuario(id_usuario integer primary key autoincrement, cedula text, nombre text, apellido text, fechaNacimiento integer, correo text, password text)")
        //db.execSQL( "CREATE TABLE turno(id integer primary key autoincrement, fk_idUsuario integer foreign key , sector text, vacuna text, Centro_Acopio text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}