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

    const val MOTIVO_MINIMO = 10

    // Regla del motivo reutilizada por el formulario y por la edición.
    fun validarMotivo(motivo: String): String? = when {
        motivo.isBlank() -> "Cuéntanos cómo te gustaría colaborar."
        motivo.trim().length < MOTIVO_MINIMO ->
            "El motivo debe tener al menos $MOTIVO_MINIMO caracteres."
        else -> null
    }

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
            fecha = when {
                fecha.isBlank() -> "Selecciona una fecha de disponibilidad."
                !fechaValida(fecha) -> "La fecha no es válida."
                else -> null
            },
            motivo = validarMotivo(motivo),

            // Aceptar el contacto es opcional.
            aceptaContacto = null
        )

    }
    private fun fechaValida(texto: String): Boolean {
        return try {
            val formato = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale("es", "CL"))
            formato.isLenient = false
            val fecha = formato.parse(texto) ?: return false
            val hoy = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.HOUR_OF_DAY, 0)
                set(java.util.Calendar.MINUTE, 0)
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }.time
            !fecha.before(hoy)
        } catch (e: Exception) {
            false
        }
    }
}