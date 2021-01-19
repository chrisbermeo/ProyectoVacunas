package com.example.proyectovacunas

import java.io.Serializable

data class Usuario(
        //está en modo prueba
        private var cedula: String,
        private var nombre:String,
        private var apellido:String,
        private var fechaNacimiento: Int,
        val correo: String,
        private var password: String
): Serializable
