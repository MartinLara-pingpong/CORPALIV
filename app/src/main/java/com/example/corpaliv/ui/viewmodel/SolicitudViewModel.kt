
package com.example.corpaliv.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.SolicitudDataStore
import com.example.corpaliv.data.SolicitudRepository
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.validation.SolicitudValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
    val cargando: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null,
    val totalSolicitudes: Int = 0,
    val solicitudes: List<SolicitudColaboracion> = emptyList()
)

class SolicitudViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val solicitudRepository = SolicitudRepository(
        SolicitudDataStore(application)
    )

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
        val resultado = SolicitudValidator.validar(
            nombre = nombre,
            correo = correo,
            edad = edad,
            fecha = fecha,
            motivo = motivo,
            aceptaContacto = aceptaContacto
        )

        return SolicitudErrores(
            nombre = resultado.nombre,
            correo = resultado.correo,
            edad = resultado.edad,
            fecha = resultado.fecha,
            motivo = resultado.motivo,
            contacto = resultado.aceptaContacto
        )
    }

    fun guardarSolicitud(solicitud: SolicitudColaboracion) {
        if (_uiState.value.enviando) return

        _uiState.value = _uiState.value.copy(
            enviando = true,
            mensaje = null,
            error = null
        )

        viewModelScope.launch {
            try {
                solicitudRepository.guardarSolicitud(solicitud)
                val solicitudes = solicitudRepository.obtenerSolicitudes()

                _uiState.value = _uiState.value.copy(
                    enviando = false,
                    mensaje = "Solicitud guardada correctamente.",
                    error = null,
                    solicitudes = solicitudes,
                    totalSolicitudes = solicitudes.size
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    enviando = false,
                    error = "No se pudo guardar la solicitud."
                )
            }
        }
    }

    fun eliminarSolicitud(id: String) {
        if (_uiState.value.enviando || _uiState.value.cargando) return

        viewModelScope.launch {
            try {
                val eliminada = solicitudRepository.eliminarSolicitud(id)

                if (eliminada) {
                    val solicitudes = solicitudRepository.obtenerSolicitudes()

                    _uiState.value = _uiState.value.copy(
                        solicitudes = solicitudes,
                        totalSolicitudes = solicitudes.size,
                        mensaje = "Solicitud eliminada correctamente.",
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "No se encontró la solicitud que quieres eliminar.",
                        mensaje = null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "No se pudo eliminar la solicitud.",
                    mensaje = null
                )
            }
        }
    }


    fun cargarSolicitudes() {
        if (_uiState.value.cargando) return

        _uiState.value = _uiState.value.copy(
            cargando = true,
            mensaje = null,
            error = null
        )

        viewModelScope.launch {
            try {
                val solicitudes = solicitudRepository.obtenerSolicitudes()

                _uiState.value = _uiState.value.copy(
                    cargando = false,
                    solicitudes = solicitudes,
                    totalSolicitudes = solicitudes.size,
                    mensaje = null,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    cargando = false,
                    error = "No se pudieron recuperar las solicitudes."
                )
            }
        }
    }
}
