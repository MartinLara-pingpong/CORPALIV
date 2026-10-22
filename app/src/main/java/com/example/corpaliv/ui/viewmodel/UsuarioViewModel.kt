
package com.example.corpaliv.ui.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.UsuarioDataStore
import com.example.corpaliv.data.UsuarioRepository
import com.example.corpaliv.model.UsuarioErrores
import com.example.corpaliv.model.UsuarioUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val usuarioRepository = UsuarioRepository(
        UsuarioDataStore(application)
    )

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())
    val errores: StateFlow<UsuarioErrores> = _errores.asStateFlow()

    init {

    }

    fun actualizarNombre(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
        _errores.update { it.copy(nombre = null) }
    }

    fun actualizarCorreo(correo: String) {
        _uiState.update { it.copy(correo = correo) }
        _errores.update { it.copy(correo = null) }
    }

    fun actualizarClave(clave: String) {
        _uiState.update { it.copy(clave = clave) }
        _errores.update { it.copy(clave = null) }
    }

    fun actualizarDireccion(direccion: String) {
        _uiState.update { it.copy(direccion = direccion) }
        _errores.update { it.copy(direccion = null) }
    }

    fun validarFormulario(): Boolean {
        val datos = _uiState.value

        val nuevosErrores = UsuarioErrores(
            nombre = if (datos.nombre.isBlank()) {
                "Introduce tu nombre completo."
            } else {
                null
            },
            correo = if (
                datos.correo.isBlank() ||
                !Patterns.EMAIL_ADDRESS
                    .matcher(datos.correo.trim())
                    .matches()
            ) {
                "Introduce un correo electrónico válido."
            } else {
                null
            },
            clave = if (datos.clave.length < 6) {
                "La contraseña debe tener al menos 6 caracteres."
            } else {
                null
            },
            direccion = if (datos.direccion.isBlank()) {
                "Introduce tu dirección."
            } else {
                null
            }
        )

        _errores.value = nuevosErrores

        return nuevosErrores.nombre == null &&
                nuevosErrores.correo == null &&
                nuevosErrores.clave == null &&
                nuevosErrores.direccion == null
    }

    fun registrarCuenta(resultado: (Boolean) -> Unit) {
        if (!validarFormulario()) {
            resultado(false)
            return
        }

        val datos = _uiState.value

        viewModelScope.launch {
            try {
                val registrada = withContext(Dispatchers.IO) {
                    usuarioRepository.registrarCuenta(
                        nombre = datos.nombre,
                        correo = datos.correo,
                        direccion = datos.direccion,
                        clave = datos.clave
                    )
                }

                if (!registrada) {
                    _errores.update {
                        it.copy(
                            correo = "Ya existe una cuenta con ese correo."
                        )
                    }
                }

                resultado(registrada)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _errores.update {
                    it.copy(
                        correo = "No se pudo registrar la cuenta. Inténtalo de nuevo."
                    )
                }
                resultado(false)
            }
        }
    }

    fun iniciarSesion(
        correo: String,
        clave: String,
        resultado: (Boolean) -> Unit
    ) {
        if (
            correo.isBlank() ||
            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() ||
            clave.isBlank()
        ) {
            resultado(false)
            return
        }

        viewModelScope.launch {
            try {
                val valido = withContext(Dispatchers.IO) {
                    usuarioRepository.validarCredenciales(
                        correo = correo,
                        clave = clave
                    )
                }

                resultado(valido)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                resultado(false)
            }
        }
    }
}
