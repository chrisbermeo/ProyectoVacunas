package com.example.proyectovacunas

import java.io.Serializable

data class Usuario(
        //está en modo prueba
        val cedula: String,
        val nombre:String,
        val apellido:String,
        val fechaNacimiento: Int,
        val correo: String,
        val password: String
): Serializable
