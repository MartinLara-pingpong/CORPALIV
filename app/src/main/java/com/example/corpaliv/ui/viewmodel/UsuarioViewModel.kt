
package com.example.corpaliv.ui.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpaliv.data.UsuarioDataStore
import com.example.corpaliv.model.UsuarioErrores
import com.example.corpaliv.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val usuarioDataStore = UsuarioDataStore(application)

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())
    val errores: StateFlow<UsuarioErrores> = _errores.asStateFlow()

    init {
        viewModelScope.launch {
            usuarioDataStore.usuario.collect { usuario ->
                _uiState.update { estado ->
                    estado.copy(
                        nombre = usuario.nombre,
                        correo = usuario.correo,
                        direccion = usuario.direccion
                    )
                }
            }
        }
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
            val registrada = usuarioDataStore.registrarCuenta(
                nombre = datos.nombre,
                correo = datos.correo,
                direccion = datos.direccion,
                clave = datos.clave
            )

            if (!registrada) {
                _errores.update {
                    it.copy(
                        correo = "Ya existe una cuenta con ese correo."
                    )
                }
            }

            resultado(registrada)
        }
    }

    fun iniciarSesion(
        correo: String,
        clave: String,
        resultado: (Boolean) -> Unit
    ) {
        if (correo.isBlank() || clave.isBlank()) {
            resultado(false)
            return
        }

        viewModelScope.launch {
            val valido = usuarioDataStore.validarCredenciales(
                correo,
                clave
            )

            resultado(valido)
        }
    }
}
