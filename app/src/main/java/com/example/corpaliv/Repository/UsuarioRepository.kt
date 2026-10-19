
package com.example.corpaliv.data

import kotlinx.coroutines.flow.Flow

class UsuarioRepository(
    private val usuarioDataStore: UsuarioDataStore
) {
    val usuario = usuarioDataStore.usuario

    suspend fun registrarCuenta(
        nombre: String,
        correo: String,
        direccion: String,
        clave: String
    ): Boolean {
        return usuarioDataStore.registrarCuenta(
            nombre = nombre,
            correo = correo,
            direccion = direccion,
            clave = clave
        )
    }

    suspend fun validarCredenciales(
        correo: String,
        clave: String
    ): Boolean {
        return usuarioDataStore.validarCredenciales(
            correo = correo,
            clave = clave
        )
    }
}
