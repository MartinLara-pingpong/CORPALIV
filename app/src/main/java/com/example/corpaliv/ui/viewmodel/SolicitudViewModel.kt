
package com.example.corpaliv.ui.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.SolicitudDataStore
import com.example.corpaliv.model.SolicitudColaboracion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

data class SolicitudErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val edad: String? = null,
    val fecha: String? = null,
    val motivo: String? = null,
    val contacto: String? = null
)

data class SolicitudUiState(
    val enviando: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null,
    val totalSolicitudes: Int = 0
)

class SolicitudViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val almacenamiento = SolicitudDataStore(application)

    private val _uiState = MutableStateFlow(SolicitudUiState())
    val uiState = _uiState.asStateFlow()

    fun validarSolicitud(
        nombre: String,
        correo: String,
        edad: String,
        fecha: String,
        motivo: String,
        aceptaContacto: Boolean
    ): SolicitudErrores {
        val edadNumero = edad.toIntOrNull()

        return SolicitudErrores(
            nombre = if (nombre.isBlank()) {
                "El nombre es obligatorio."
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
                "Introduce una edad entre 18 y 120 años."
            } else null,

            fecha = if (!fechaEsValida(fecha)) {
                "Introduce una fecha real con formato DD/MM/AAAA."
            } else null,

            motivo = if (motivo.isBlank()) {
                "Cuéntanos cómo quieres colaborar."
            } else null,

            contacto = if (!aceptaContacto) {
                "Debes aceptar que CORPALIV te contacte."
            } else null
        )
    }



    private fun fechaEsValida(fecha: String): Boolean {
        val partes = fecha.trim().split("/")
        if (partes.size != 3) return false
        val dia = partes[0].toIntOrNull() ?: return false
        val mes = partes[1].toIntOrNull() ?: return false
        val anio = partes[2].toIntOrNull() ?: return false
        if (partes[0].length != 2 ||
            partes[1].length != 2 ||
            partes[2].length != 4
        ) {
            return false
        }
        if (anio !in 1900..9999 || mes !in 1..12) {
            return false
        }
        val diasPorMes = when (mes) {
            4, 6, 9, 11 -> 30
            2 -> {
                val bisiesto = anio % 400 == 0 ||
                        (anio % 4 == 0 && anio % 100 != 0)

                if (bisiesto) 29 else 28
            }
            else -> 31
        }
        return dia in 1..diasPorMes
    }



    fun guardarSolicitud(solicitud: SolicitudColaboracion) {
        if (_uiState.value.enviando) return

        _uiState.value = SolicitudUiState(enviando = true)

        viewModelScope.launch {
            try {
                almacenamiento.guardarSolicitud(solicitud)
                val solicitudes = almacenamiento.obtenerSolicitudes()

                _uiState.value = SolicitudUiState(
                    mensaje = "Solicitud guardada correctamente.",
                    totalSolicitudes = solicitudes.size
                )
            } catch (e: Exception) {
                _uiState.value = SolicitudUiState(
                    error = "No se pudo guardar la solicitud."
                )
            }
        }
    }

    fun cargarSolicitudes() {
        viewModelScope.launch {
            try {
                val solicitudes = almacenamiento.obtenerSolicitudes()

                _uiState.value = _uiState.value.copy(
                    totalSolicitudes = solicitudes.size,
                    mensaje = "Solicitudes recuperadas correctamente.",
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "No se pudieron recuperar las solicitudes."
                )
            }
        }
    }
}
