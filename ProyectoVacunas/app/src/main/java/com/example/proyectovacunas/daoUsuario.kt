package com.example.proyectovacunas

import android.content.Context
import android.database.sqlite.SQLiteDatabase

data class daoUsuario(
        //está en modo prueba
        val c: Context,
        val u: Usuario,
        val lista: ArrayList<Usuario>,
        //var DB = UserSqliteOpenHelper,
        val sql: SQLiteDatabase,
        val bd_name: String="BD_usuarios",

        )
