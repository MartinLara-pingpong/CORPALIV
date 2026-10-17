
package com.example.corpaliv.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.corpaliv.model.UsuarioErrores
import com.example.corpaliv.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState.asStateFlow()

    private val _errores = MutableStateFlow(UsuarioErrores())
    val errores: StateFlow<UsuarioErrores> = _errores.asStateFlow()

    fun actualizarNombre(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }

    fun actualizarCorreo(correo: String) {
        _uiState.update { it.copy(correo = correo) }
    }

    fun actualizarClave(clave: String) {
        _uiState.update { it.copy(clave = clave) }
    }

    fun actualizarDireccion(direccion: String) {
        _uiState.update { it.copy(direccion = direccion) }
    }

    fun validarFormulario(): Boolean {
        val datos = _uiState.value

        val nuevosErrores = UsuarioErrores(
            nombre = if (datos.nombre.isBlank()) {
                "El nombre es obligatorio"
            } else null,
            correo = if (
                datos.correo.isBlank() ||
                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(datos.correo.trim()).matches()
            ) {
                "Introduce un correo válido"
            } else null,
            clave = if (datos.clave.length < 6) {
                "La clave debe tener al menos 6 caracteres"
            } else null,
            direccion = if (datos.direccion.isBlank()) {
                "La dirección es obligatoria"
            } else null
        )

        _errores.value = nuevosErrores

        return nuevosErrores == UsuarioErrores()
    }
}
