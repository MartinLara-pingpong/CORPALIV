
package com.example.corpaliv.validation

import android.util.Patterns

data class ResultadoValidacionSolicitud(
    val nombre: String? = null,
    val correo: String? = null,
    val edad: String? = null,
    val fecha: String? = null,
    val motivo: String? = null,
    val aceptaContacto: String? = null
) {
    val esValido: Boolean
        get() = nombre == null &&
                correo == null &&
                edad == null &&
                fecha == null &&
                motivo == null &&
                aceptaContacto == null
}

object SolicitudValidator {

    fun validar(
        nombre: String,
        correo: String,
        edad: String,
        fecha: String,
        motivo: String,
        aceptaContacto: Boolean
    ): ResultadoValidacionSolicitud {

        val edadNumero = edad.toIntOrNull()

        return ResultadoValidacionSolicitud(
            nombre = if (nombre.isBlank()) {
                "Introduce tu nombre."
            } else {
                null
            },

            correo = if (
                !Patterns.EMAIL_ADDRESS
                    .matcher(correo.trim())
                    .matches()
            ) {
                "Introduce un correo electrónico válido."
            } else {
                null
            },

            edad = if (
                edadNumero == null || edadNumero !in 18..120
            ) {
                "La edad debe estar entre 18 y 120 años."
            } else {
                null
            },

            // Este campo es disponibilidad, no una fecha concreta.
            fecha = if (fecha.isBlank()) {
                "Indica cuándo tienes disponibilidad para colaborar."
            } else {
                null
            },

            motivo = if (motivo.isBlank()) {
                "Cuéntanos cómo te gustaría colaborar."
            } else {
                null
            },

            // Aceptar el contacto es opcional.
            aceptaContacto = null
        )
    }
}
