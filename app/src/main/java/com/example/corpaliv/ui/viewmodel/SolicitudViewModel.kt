
package com.example.corpaliv.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.SolicitudDataStore
import com.example.corpaliv.data.SolicitudRepository
import com.example.corpaliv.model.SolicitudColaboracion
import com.example.corpaliv.validation.SolicitudValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

data class SolicitudErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val edad: String? = null,
    val fecha: String? = null,
    val motivo: String? = null,
    val contacto: String? = null
)

data class SolicitudFormularioState(
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val edad: String = "",
    val fecha: String = "",
    val motivo: String = "",
    val aceptaContacto: Boolean = false,
    val disponibilidadRegular: Boolean = false,
    val recibirNovedades: Boolean = false,
    val fotografia: Bitmap? = null,
    val errorFotografia: String? = null,
    val errores: SolicitudErrores = SolicitudErrores()
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

    private val _formularioState =
        MutableStateFlow(SolicitudFormularioState())
    val formularioState = _formularioState.asStateFlow()

    fun actualizarNombre(valor: String) {
        _formularioState.update {
            it.copy(
                nombre = valor,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun actualizarCorreo(valor: String) {
        _formularioState.update {
            it.copy(
                correo = valor,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun actualizarTelefono(valor: String) {
        _formularioState.update {
            it.copy(
                telefono = valor,
                errores = it.errores.copy(telefono = null)
            )
        }
    }

    fun actualizarEdad(valor: String) {
        if (valor.all { it.isDigit() }) {
            _formularioState.update {
                it.copy(
                    edad = valor,
                    errores = it.errores.copy(edad = null)
                )
            }
        }
    }

    fun actualizarFecha(valor: String) {
        _formularioState.update {
            it.copy(
                fecha = valor,
                errores = it.errores.copy(fecha = null)
            )
        }
    }

    fun actualizarMotivo(valor: String) {
        _formularioState.update {
            it.copy(
                motivo = valor,
                errores = it.errores.copy(motivo = null)
            )
        }
    }

    fun actualizarAceptaContacto(valor: Boolean) {
        _formularioState.update {
            it.copy(
                aceptaContacto = valor,
                telefono = if (valor) it.telefono else "",
                errores = it.errores.copy(
                    contacto = null,
                    telefono = null
                )
            )
        }
    }

    fun actualizarDisponibilidadRegular(valor: Boolean) {
        _formularioState.update {
            it.copy(disponibilidadRegular = valor)
        }
    }

    fun actualizarRecibirNovedades(valor: Boolean) {
        _formularioState.update {
            it.copy(recibirNovedades = valor)
        }
    }

    fun actualizarFotografia(bitmap: Bitmap?) {
        _formularioState.update {
            it.copy(
                fotografia = bitmap,
                errorFotografia = null
            )
        }
    }

    fun actualizarErrorFotografia(mensaje: String?) {
        _formularioState.update {
            it.copy(errorFotografia = mensaje)
        }
    }

    fun guardarFotografia(
        bitmap: Bitmap,
        idSolicitud: String
    ): String? {
        val nombreArchivo = "solicitud_$idSolicitud.jpg"
        val archivo = File(
            getApplication<Application>().filesDir,
            nombreArchivo
        )

        return try {
            val guardada = FileOutputStream(archivo).use { salida ->
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    90,
                    salida
                )
            }

            if (guardada) {
                nombreArchivo
            } else {
                archivo.delete()
                null
            }
        } catch (e: Exception) {
            archivo.delete()
            null
        }
    }

    fun validarFormulario(): Boolean {
        val formulario = _formularioState.value

        val resultado = SolicitudValidator.validar(
            nombre = formulario.nombre,
            correo = formulario.correo,
            edad = formulario.edad,
            fecha = formulario.fecha,
            motivo = formulario.motivo,
            aceptaContacto = formulario.aceptaContacto
        )

        val telefonoLimpio = formulario.telefono.filter {
            it.isDigit()
        }

        val errorTelefono = when {
            formulario.aceptaContacto &&
                    formulario.telefono.isBlank() ->
                "Introduce tu número de teléfono."

            formulario.aceptaContacto &&
                    telefonoLimpio.length < 8 ->
                "Introduce un número de teléfono válido."

            else -> null
        }

        val errores = SolicitudErrores(
            nombre = resultado.nombre,
            correo = resultado.correo,
            telefono = errorTelefono,
            edad = resultado.edad,
            fecha = resultado.fecha,
            motivo = resultado.motivo,
            contacto = resultado.aceptaContacto
        )

        _formularioState.update {
            it.copy(errores = errores)
        }

        return errores.nombre == null &&
                errores.correo == null &&
                errores.telefono == null &&
                errores.edad == null &&
                errores.fecha == null &&
                errores.motivo == null &&
                errores.contacto == null
    }

    fun limpiarFormulario() {
        _formularioState.value = SolicitudFormularioState()
    }

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

        _uiState.update {
            it.copy(
                enviando = true,
                mensaje = null,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                solicitudRepository.guardarSolicitud(solicitud)

                val solicitudes =
                    solicitudRepository.obtenerSolicitudes()

                _uiState.update {
                    it.copy(
                        enviando = false,
                        mensaje = "Solicitud guardada correctamente.",
                        error = null,
                        solicitudes = solicitudes,
                        totalSolicitudes = solicitudes.size
                    )
                }

                limpiarFormulario()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        enviando = false,
                        error = "No se pudo guardar la solicitud."
                    )
                }
            }
        }
    }

    fun actualizarSolicitud(solicitud: SolicitudColaboracion) {
        if (_uiState.value.enviando || _uiState.value.cargando) return

        _uiState.update {
            it.copy(
                enviando = true,
                mensaje = null,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val actualizada =
                    solicitudRepository.actualizarSolicitud(solicitud)

                if (actualizada) {
                    val solicitudes =
                        solicitudRepository.obtenerSolicitudes()

                    _uiState.update {
                        it.copy(
                            enviando = false,
                            solicitudes = solicitudes,
                            totalSolicitudes = solicitudes.size,
                            mensaje = "Solicitud actualizada correctamente.",
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            enviando = false,
                            error = "No se encontró la solicitud que quieres actualizar."
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        enviando = false,
                        error = "No se pudo actualizar la solicitud."
                    )
                }
            }
        }
    }

    fun eliminarSolicitud(id: String) {
        if (_uiState.value.enviando || _uiState.value.cargando) return

        viewModelScope.launch {
            try {
                val eliminada =
                    solicitudRepository.eliminarSolicitud(id)

                if (eliminada) {
                    val solicitudes =
                        solicitudRepository.obtenerSolicitudes()

                    _uiState.update {
                        it.copy(
                            solicitudes = solicitudes,
                            totalSolicitudes = solicitudes.size,
                            mensaje = "Solicitud eliminada correctamente.",
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            error = "No se encontró la solicitud que quieres eliminar.",
                            mensaje = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "No se pudo eliminar la solicitud.",
                        mensaje = null
                    )
                }
            }
        }
    }

    fun cargarSolicitudes() {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(
                cargando = true,
                mensaje = null,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val solicitudes =
                    solicitudRepository.obtenerSolicitudes()

                _uiState.update {
                    it.copy(
                        cargando = false,
                        solicitudes = solicitudes,
                        totalSolicitudes = solicitudes.size,
                        mensaje = null,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudieron recuperar las solicitudes."
                    )
                }
            }
        }
    }

    fun cargarSolicitudesPropias(correo: String) {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(
                cargando = true,
                mensaje = null,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val solicitudes =
                    solicitudRepository.obtenerSolicitudesPorCorreo(correo)

                _uiState.update {
                    it.copy(
                        cargando = false,
                        solicitudes = solicitudes,
                        totalSolicitudes = solicitudes.size,
                        mensaje = null,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudieron cargar tus colaboraciones."
                    )
                }
            }
        }
    }

    fun eliminarSolicitudPropia(
        id: String,
        correoPropietario: String
    ) {
        if (
            correoPropietario.isBlank() ||
            _uiState.value.enviando ||
            _uiState.value.cargando
        ) return

        viewModelScope.launch {
            try {
                val eliminada = solicitudRepository.eliminarSolicitudPropia(
                    id = id,
                    correoPropietario = correoPropietario
                )

                val solicitudes =
                    solicitudRepository.obtenerSolicitudesPorCorreo(
                        correoPropietario
                    )

                _uiState.update {
                    it.copy(
                        solicitudes = solicitudes,
                        totalSolicitudes = solicitudes.size,
                        mensaje = if (eliminada) {
                            "Tu solicitud se ha eliminado."
                        } else {
                            null
                        },
                        error = if (eliminada) {
                            null
                        } else {
                            "No se encontró una solicitud tuya con ese identificador."
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "No se pudo eliminar tu solicitud.")
                }
            }
        }
    }

}
