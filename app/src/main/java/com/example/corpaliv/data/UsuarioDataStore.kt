
package com.example.corpaliv.data

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private val Context.usuarioDataStore by preferencesDataStore(
    name = "corpaliv_usuario"
)

data class UsuarioGuardado(
    val nombre: String = "",
    val correo: String = "",
    val direccion: String = ""
)

class UsuarioDataStore(
    private val context: Context
) {
    private object Keys {
        val NOMBRE = stringPreferencesKey("nombre")
        val CORREO = stringPreferencesKey("correo")
        val DIRECCION = stringPreferencesKey("direccion")
        val SAL = stringPreferencesKey("clave_salt")
        val HASH = stringPreferencesKey("clave_hash")
    }

    val usuario: Flow<UsuarioGuardado> =
        context.usuarioDataStore.data.map { preferencias ->
            UsuarioGuardado(
                nombre = preferencias[Keys.NOMBRE] ?: "",
                correo = preferencias[Keys.CORREO] ?: "",
                direccion = preferencias[Keys.DIRECCION] ?: ""
            )
        }

    suspend fun guardarUsuario(
        nombre: String,
        correo: String,
        direccion: String
    ) {
        context.usuarioDataStore.edit { preferencias ->
            preferencias[Keys.NOMBRE] = nombre
            preferencias[Keys.CORREO] = correo.trim().lowercase()
            preferencias[Keys.DIRECCION] = direccion
        }
    }

    suspend fun registrarCuenta(
        nombre: String,
        correo: String,
        direccion: String,
        clave: String
    ): Boolean {
        val correoNormalizado = correo.trim().lowercase()
        val preferenciasActuales = context.usuarioDataStore.data.first()

        if (preferenciasActuales[Keys.CORREO] == correoNormalizado &&
            preferenciasActuales[Keys.HASH] != null
        ) {
            return false
        }

        val sal = ByteArray(16)
        SecureRandom().nextBytes(sal)

        val hash = generarHash(clave, sal)

        context.usuarioDataStore.edit { preferencias ->
            preferencias[Keys.NOMBRE] = nombre.trim()
            preferencias[Keys.CORREO] = correoNormalizado
            preferencias[Keys.DIRECCION] = direccion.trim()
            preferencias[Keys.SAL] = Base64.encodeToString(sal, Base64.NO_WRAP)
            preferencias[Keys.HASH] = Base64.encodeToString(hash, Base64.NO_WRAP)
        }

        return true
    }

    suspend fun validarCredenciales(
        correo: String,
        clave: String
    ): Boolean {
        val preferencias = context.usuarioDataStore.data.first()

        val correoGuardado = preferencias[Keys.CORREO] ?: return false
        val salGuardada = preferencias[Keys.SAL] ?: return false
        val hashGuardado = preferencias[Keys.HASH] ?: return false

        if (correo.trim().lowercase() != correoGuardado) {
            return false
        }

        val sal = Base64.decode(salGuardada, Base64.NO_WRAP)
        val hashEsperado = Base64.decode(hashGuardado, Base64.NO_WRAP)
        val hashIntroducido = generarHash(clave, sal)

        return java.security.MessageDigest.isEqual(
            hashEsperado,
            hashIntroducido
        )
    }

    suspend fun borrarUsuario() {
        context.usuarioDataStore.edit { preferencias ->
            preferencias.remove(Keys.NOMBRE)
            preferencias.remove(Keys.CORREO)
            preferencias.remove(Keys.DIRECCION)
            preferencias.remove(Keys.SAL)
            preferencias.remove(Keys.HASH)
        }
    }

    private fun generarHash(
        clave: String,
        sal: ByteArray
    ): ByteArray {
        val especificacion = PBEKeySpec(
            clave.toCharArray(),
            sal,
            120_000,
            256
        )

        return try {
            SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(especificacion)
                .encoded
        } finally {
            especificacion.clearPassword()
        }
    }
}
