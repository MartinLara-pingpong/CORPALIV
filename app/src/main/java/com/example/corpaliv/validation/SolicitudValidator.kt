
package com.example.corpaliv.validation

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        val formatoFecha = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        ).apply {
            isLenient = false
        }

        val fechaValida = try {
            val fechaParseada = formatoFecha.parse(fecha)

            fechaParseada != null &&
                    formatoFecha.format(fechaParseada) == fecha &&
                    !fechaParseada.before(
                        Calendar.getInstance().apply {
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.time
                    )
        } catch (_: Exception) {
            false
        }

        return ResultadoValidacionSolicitud(
            nombre = if (nombre.isBlank()) {
                "Introduce tu nombre."
            } else null,

            correo = if (
                !Patterns.EMAIL_ADDRESS
                    .matcher(correo.trim())
                    .matches()
            ) {
                "Introduce un correo válido."
            } else null,

            edad = if (
                edadNumero == null || edadNumero !in 18..120
            ) {
                "La edad debe estar entre 18 y 120 años."
            } else null,

            fecha = if (!fechaValida) {
                "Introduce una fecha válida (DD/MM/AAAA), hoy o posterior."
            } else null,

            motivo = if (motivo.isBlank()) {
                "Indica el motivo de tu solicitud."
            } else null,

            aceptaContacto = if (!aceptaContacto) {
                "Debes aceptar que contactemos contigo."
            } else null
        )
    }
}
