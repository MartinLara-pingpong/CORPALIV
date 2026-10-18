
package com.example.corpaliv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.corpaliv.ui.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegistroClick: () -> Unit,
    usuarioViewModel: UsuarioViewModel
) {
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }
    var cargando by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Iniciar sesión")
        Text("Accede a tu cuenta de CORPALIV.")

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                error = false
            },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = clave,
            onValueChange = {
                clave = it
                error = false
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (error) {
            Text("Correo o contraseña incorrectos. Comprueba tus datos y vuelve a intentarlo.")
        }

        Button(
            onClick = {
                if (correo.isBlank() || clave.isBlank()) {
                    error = true
                } else {
                    cargando = true
                    usuarioViewModel.iniciarSesion(correo, clave) { valido ->
                        cargando = false
                        if (valido) {
                            onLoginSuccess()
                        } else {
                            error = true
                        }
                    }
                }
            },
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (cargando) "Comprobando..." else "Entrar")
        }

        TextButton(
            onClick = onRegistroClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Crear una cuenta")
        }
    }
}
